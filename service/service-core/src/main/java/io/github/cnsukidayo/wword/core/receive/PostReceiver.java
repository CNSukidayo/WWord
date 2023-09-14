package io.github.cnsukidayo.wword.core.receive;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.rabbitmq.client.Channel;
import io.github.cnsukidayo.wword.common.config.properties.WWordProperties;
import io.github.cnsukidayo.wword.common.utils.FileUtils;
import io.github.cnsukidayo.wword.core.service.PostService;
import io.github.cnsukidayo.wword.global.exception.FileHandlerException;
import io.github.cnsukidayo.wword.model.entity.Post;
import io.github.cnsukidayo.wword.model.enums.PostStatus;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import io.github.cnsukidayo.wword.mq.constant.MQConst;
import io.github.cnsukidayo.wword.oss.component.OSSComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author sukidayo
 * @date 2023/7/20 15:33
 */
@Component
public class PostReceiver {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final PostService postService;

    private final OSSComponent ossComponent;

    private final WWordProperties wWordProperties;

    public PostReceiver(PostService postService,
                        OSSComponent ossComponent,
                        WWordProperties wWordProperties) {
        this.postService = postService;
        this.ossComponent = ossComponent;
        this.wWordProperties = wWordProperties;
    }

    @RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = MQConst.EXCHANGE_POST_DIRECT),
        value = @Queue(value = MQConst.QUEUE_PUBLISH_POST, durable = "true"),
        key = {MQConst.ROUTING_PUBLISH_POST}
    ))
    public void publishPost(Post post, Message message, Channel channel) throws IOException {
        Assert.notNull(post, "post must not be null");
        log.info("handle post userId:[{}],file url:[{}]", post.getUuid(), post.getOriginUrl());
        boolean hasMarkdown = false;
        ZipFile zipFile = null;
        BufferedInputStream bufferedInputStream = null;
        File targetFilePath = null;
        File markdown = null;
        try {
            postService.save(post);
            // 下载文件,得到目标文件在本机的位置
            targetFilePath = new File(ossComponent.downloadFile(post.getOriginUrl(), wWordProperties.getResourceLocations() + WWordConst.HANDLE_POST_PATH));
            // 判断目标文件是否是压缩包
            String fileName = FileUtil.getName(targetFilePath);
            String suffix = FileUtil.getSuffix(fileName);

            if (!FileUtils.ZIP_SUFFIX.equalsIgnoreCase(suffix)
                && !FileUtils.RAR_SUFFIX.equalsIgnoreCase(suffix)
                && !FileUtils.TAR_SUFFIX.equalsIgnoreCase(suffix)
                && !FileUtils.SevenZ_SUFFIX.equalsIgnoreCase(suffix)
                && !FileUtils.TAZ_SUFFIX.equalsIgnoreCase(suffix)) {
                throw new FileHandlerException("上传的文件类型不合法!");
            }
            zipFile = new ZipFile(targetFilePath, Charset.forName("GBK"));
            Iterator<? extends ZipEntry> iterator = zipFile.entries().asIterator();
            while (iterator.hasNext()) {
                ZipEntry zipEntry = iterator.next();
                String entryName = zipEntry.getName();
                if (FileUtil.isFile(entryName) && !WWordConst.allowMarkdownSuffix.contains(FileUtil.getSuffix(entryName)))
                    throw new FileHandlerException("上传压缩包中含有不允许的文件类型: " + entryName);

                if (FileUtil.getSuffix(entryName).equalsIgnoreCase("md")) {
                    if (hasMarkdown) throw new FileHandlerException("markdown文件数量多于一个!");
                    hasMarkdown = true;
                }
            }
            if (!hasMarkdown) {
                throw new FileHandlerException("没有找到markdown文件");
            }
            // 解压文件
            File unzipFile = ZipUtil.unzip(targetFilePath, targetFilePath.getParentFile(), Charset.forName("GBK"));
            // 得到唯一的markdown文件

            java.util.Queue<File> listFiles = new ArrayDeque<>(List.of(Optional.ofNullable(unzipFile.listFiles())
                .orElseThrow(() -> new FileHandlerException("上传的压缩包内容为空!"))));
            while (!listFiles.isEmpty()) {
                File listFile = listFiles.poll();
                if (listFile.isDirectory()) {
                    listFiles.addAll(List.of(Optional.ofNullable(listFile.listFiles()).orElse(new File[]{})));
                }
                if (listFile.isFile() && FileUtil.getSuffix(listFile).equalsIgnoreCase("md")) {
                    markdown = listFile;
                    break;
                }
            }
            Optional.ofNullable(markdown).orElseThrow(() -> new FileHandlerException("没有找到markdown文件!"));
            // 读取markdown文件
            bufferedInputStream = new BufferedInputStream(new FileInputStream(markdown));

            // 更多的操作

            // 将markdown文件的内容更新到数据库中
            post.setContent(new String(bufferedInputStream.readAllBytes(), StandardCharsets.UTF_8));
            post.setPostStatus(PostStatus.SUCCESS);
            post.setDescribeInfo(PostStatus.SUCCESS.getValue());
            postService.updateById(post);
            log.info("publish post success,post id:[{}]", post.getId());
        } catch (FileHandlerException e) {
            // 更新帖子状态
            log.error("publishPost Fail,postID:[{}]", post.getId());
            post.setPostStatus(PostStatus.PUBLISH_FAIL);
            post.setDescribeInfo(e.getMessage());
            postService.updateById(post);
            e.printStackTrace();
        } catch (Exception e) {
            // 更新帖子状态
            log.error("publishPost Fail,postID:[{}]", post.getId());
            post.setPostStatus(PostStatus.PUBLISH_FAIL);
            post.setDescribeInfo(PostStatus.PUBLISH_FAIL.getValue());
            postService.updateById(post);
            e.printStackTrace();
        } finally {
            // 放弃这条消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            IoUtil.close(bufferedInputStream);
            IoUtil.close(zipFile);
            // 删除压缩包
            if (targetFilePath != null && Files.exists(targetFilePath.toPath())) {
                Files.delete(targetFilePath.toPath());
            }
            // 删除markdown
            if (markdown != null && Files.exists(markdown.toPath())) {
                Files.delete(markdown.toPath());
            }

        }
    }

}
