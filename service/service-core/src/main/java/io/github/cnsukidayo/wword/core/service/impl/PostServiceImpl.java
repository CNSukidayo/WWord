package io.github.cnsukidayo.wword.core.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.common.utils.ValidationUtils;
import io.github.cnsukidayo.wword.core.dao.PostMapper;
import io.github.cnsukidayo.wword.core.service.PostService;
import io.github.cnsukidayo.wword.global.support.constant.MQConst;
import io.github.cnsukidayo.wword.global.support.enums.FileBasePath;
import io.github.cnsukidayo.wword.global.utils.FileUtils;
import io.github.cnsukidayo.wword.model.entity.Post;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.enums.PostStatus;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import io.github.cnsukidayo.wword.model.param.PublishPostParam;
import io.github.cnsukidayo.wword.mq.service.RabbitService;
import io.github.cnsukidayo.wword.oss.component.OSSComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author sukidayo
 * @date 2023/9/12 18:42
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    private final RabbitService rabbitService;

    private final OSSComponent ossComponent;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public PostServiceImpl(RabbitService rabbitService,
                           OSSComponent ossComponent) {
        this.rabbitService = rabbitService;
        this.ossComponent = ossComponent;
    }

    @Override
    public void publishPost(PublishPostParam publishPostParam, User user) {
        Assert.notNull(publishPostParam, "publishPostParam must not be null");
        Assert.notNull(user, "user param must not be null");
        // 参数校验
        ValidationUtils.validate(publishPostParam);
        DateTime dateTime = new DateTime();
        // 文件上传
        String originUrl = null;
        MultipartFile multipartFile = publishPostParam.getFile();
        try {
            originUrl = ossComponent.fileUpLoadAutoRename(multipartFile.getInputStream(),
                FileUtils.separatorFilePath(WWordConst.separatorChar,
                    FileBasePath.FileNameSpace.USER.getBasePath(),
                    FileBasePath.FileCategory.POST.getBasePath(),
                    FileBasePath.FileBasePathDIR.UPLOAD_DIR.getBasePath(),
                    dateTime.toString(WWordConst.dateFormat)),
                multipartFile.getOriginalFilename());
        } catch (IOException e) {
            logger.error("PostServiceImpl-publishPost-uploadFileError;FileName:[{}]", multipartFile.getOriginalFilename());
            throw new RuntimeException(e);
        }

        // 构造消息
        Post post = new Post();
        post.setUuid(user.getUuid());
        post.setTitle(publishPostParam.getTitle());
        post.setPostStatus(PostStatus.TO_CHECK);
        post.setOriginUrl(originUrl);
        LocalDateTime localDateTime = dateTime.toLocalDateTime();
        post.setCreateTime(localDateTime);

        // 发布到MQ中
        rabbitService.sendMessage(MQConst.EXCHANGE_POST_DIRECT, MQConst.ROUTING_PUBLISH_POST, post);
    }
}
