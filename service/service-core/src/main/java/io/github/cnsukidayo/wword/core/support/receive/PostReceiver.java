package io.github.cnsukidayo.wword.core.support.receive;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.rabbitmq.client.Channel;
import io.github.cnsukidayo.wword.core.service.PostService;
import io.github.cnsukidayo.wword.global.exception.FileHandlerException;
import io.github.cnsukidayo.wword.global.support.constant.MQConst;
import io.github.cnsukidayo.wword.global.support.enums.FileBasePath;
import io.github.cnsukidayo.wword.global.utils.FileUtils;
import io.github.cnsukidayo.wword.model.entity.Post;
import io.github.cnsukidayo.wword.model.enums.PostStatus;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import io.github.cnsukidayo.wword.oss.component.OSSComponent;
import io.github.cnsukidayo.wword.oss.config.properties.OSSProperties;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Image;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.Renderer;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * @author sukidayo
 * @date 2023/7/20 15:33
 */
@Component
public class PostReceiver {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PostService postService;

    private final OSSComponent ossComponent;

    private final OSSProperties ossProperties;

    private final Parser markDownParser;

    private final Renderer markDownRenderer;

    public PostReceiver(PostService postService,
                        OSSComponent ossComponent,
                        OSSProperties ossProperties,
                        Parser markDownParser,
                        Renderer markDownRenderer) {
        this.postService = postService;
        this.ossComponent = ossComponent;
        this.ossProperties = ossProperties;
        this.markDownParser = markDownParser;
        this.markDownRenderer = markDownRenderer;
    }

    @RabbitListener(
        bindings = @QueueBinding(
            exchange = @Exchange(value = MQConst.EXCHANGE_POST_DIRECT),
            value = @Queue(value = MQConst.QUEUE_PUBLISH_POST, durable = "true"),
            key = {MQConst.ROUTING_PUBLISH_POST}
        )
    )
    public void publishPost(Post post, Message message, Channel channel) throws IOException {
        Assert.notNull(post, "post must not be null");
        logger.info("handle post userId:[{}],file url:[{}]", post.getUuid(), post.getOriginUrl());
        boolean hasMarkdown = false;
        ZipFile zipFile = null;
        BufferedInputStream bufferedInputStream = null;
        File markdown = null;
        Path targetPrefix;
        String createTime = new DateTime(post.getCreateTime().atZone(ZoneId.systemDefault()).toInstant())
            .toString(WWordConst.dateFormat);
        // 保存当前的帖子信息
        postService.save(post);
        File targetFilePath = new File(ossComponent.downloadFile(post.getOriginUrl(),
            FileUtils.separatorFilePath(WWordConst.separatorChar,
                ossProperties.getWorkSpaceDir(),
                FileBasePath.FileNameSpace.USER.getBasePath(),
                FileBasePath.FileCategory.POST.getBasePath(),
                FileBasePath.FileBasePathDIR.WORK_DIR.getBasePath(),
                createTime)));
        // 判断目标文件是否是压缩包
        String fileName = FileUtil.getName(targetFilePath);
        String suffix = FileUtil.getSuffix(fileName);
        // 读取到的markdown原文
        String markDownOrigin = null;
        try {
            if (!FileUtils.ZIP_SUFFIX.equalsIgnoreCase(suffix)
                && !FileUtils.RAR_SUFFIX.equalsIgnoreCase(suffix)
                && !FileUtils.TAR_SUFFIX.equalsIgnoreCase(suffix)
                && !FileUtils.SevenZ_SUFFIX.equalsIgnoreCase(suffix)
                && !FileUtils.TAZ_SUFFIX.equalsIgnoreCase(suffix)) {
                throw new FileHandlerException("上传的文件类型不合法!");
            }
            zipFile = createZipFile(targetFilePath);

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
            File unzipFile = ZipUtil.unzip(zipFile, targetFilePath.getParentFile());
            // 遍历得到唯一的markdown文件
            java.util.Queue<File> listFiles = new ArrayDeque<>(List.of(Optional.ofNullable(unzipFile.listFiles())
                .orElseThrow(() -> new FileHandlerException("上传的压缩包内容为空!"))));
            while (!listFiles.isEmpty()) {
                File listFileEntry = listFiles.poll();
                if (listFileEntry.isDirectory()) {
                    listFiles.addAll(List.of(Optional.ofNullable(listFileEntry.listFiles()).orElse(new File[]{})));
                }
                if (listFileEntry.isFile() && FileUtil.getSuffix(listFileEntry).equalsIgnoreCase("md")) {
                    markdown = listFileEntry;
                    break;
                }
            }
            Optional.ofNullable(markdown).orElseThrow(() -> new FileHandlerException("没有找到markdown文件!"));
            // 读取markdown文件
            bufferedInputStream = new BufferedInputStream(new FileInputStream(markdown));

            // 将文件中的图片等静态资源发布
            Path origin = targetFilePath.getParentFile().toPath();
            targetPrefix = Paths.get(FileUtils.separatorFilePath(WWordConst.separatorChar,
                FileBasePath.FileNameSpace.PUBLIC.getBasePath(),
                FileBasePath.FileCategory.POST.getBasePath(),
                FileBasePath.FileBasePathDIR.PUBLIC_DIR.getBasePath(),
                createTime,
                UUID.randomUUID().toString().replaceAll("-", "")));
            listFiles.clear();
            listFiles.addAll(List.of(Optional.ofNullable(origin.toFile().listFiles()).orElse(new File[]{})));
            while (!listFiles.isEmpty()) {
                File fileEntry = listFiles.poll();
                if (fileEntry.isDirectory()) {
                    listFiles.addAll(List.of(Optional.ofNullable(fileEntry.listFiles()).orElse(new File[]{})));
                }
                if (fileEntry.isFile() && WWordConst.allowPublicSuffix.contains(FileUtil.getSuffix(fileEntry))) {
                    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileEntry))) {
                        ossComponent.fileUpLoadOriginName(bis, targetPrefix.resolve(origin.relativize(fileEntry.toPath())).toString());
                    }
                }
            }
            markDownOrigin = new String(bufferedInputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (FileHandlerException e) {
            // 更新帖子状态
            logger.error("publishPost Fail,postID:[{}]", post.getId());
            post.setPostStatus(PostStatus.PUBLISH_FAIL);
            post.setDescribeInfo(e.getMessage());
            postService.updateById(post);
            throw new RuntimeException(e);
        } catch (Exception e) {
            // 更新帖子状态
            logger.error("publishPost Fail,postID:[{}]", post.getId());
            post.setPostStatus(PostStatus.PUBLISH_FAIL);
            post.setDescribeInfo(PostStatus.PUBLISH_FAIL.getValue());
            postService.updateById(post);
            throw new RuntimeException(e);
        } finally {
            // 放弃这条消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            IoUtil.close(bufferedInputStream);
            IoUtil.close(zipFile);
        }

        // 修改图片的路径为发布的路径
        Node parseNode = markDownParser.parse(markDownOrigin);
        parseNode.accept(new AbstractVisitor() {
            @Override
            public void visit(Image image) {
                Path destination = Paths.get(image.getDestination());
                image.setDestination(targetPrefix.resolve(destination).toString().replace("\\","/"));
                visitChildren(image);
            }
        });
        String markDownRender = markDownRenderer.render(parseNode);

        // 将markdown文件的内容更新到数据库中
        post.setContent(markDownRender);
        post.setPostStatus(PostStatus.SUCCESS);
        post.setDescribeInfo(PostStatus.SUCCESS.getValue());
        postService.updateById(post);
        logger.info("publish post success,post id:[{}]", post.getId());

    }

    public ZipFile createZipFile(File targetFilePath) throws IOException {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(targetFilePath, StandardCharsets.UTF_8);
            return zipFile;
        } catch (IOException e) {
            logger.error("Failed to unzip with charset [{}]; filePath:[{}]",
                StandardCharsets.UTF_8,
                targetFilePath.getAbsolutePath());
        }
        try {
            zipFile = new ZipFile(targetFilePath, Charset.forName("GBK"));
            return zipFile;
        } catch (IOException e) {
            logger.error("Failed to unzip with charset [{}]; filePath:[{}]",
                Charset.forName("GBK"),
                targetFilePath.getAbsolutePath());
        }
        throw new ZipException("invalid CEN header (bad entry name)");
    }

}
