package io.github.cnsukidayo.wword.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.common.utils.ValidationUtils;
import io.github.cnsukidayo.wword.core.dao.PostMapper;
import io.github.cnsukidayo.wword.core.service.PostService;
import io.github.cnsukidayo.wword.model.entity.Post;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.enums.PostStatus;
import io.github.cnsukidayo.wword.model.param.PublishPostParam;
import io.github.cnsukidayo.wword.mq.constant.MQConst;
import io.github.cnsukidayo.wword.mq.service.RabbitService;
import io.github.cnsukidayo.wword.oss.component.OSSComponent;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author sukidayo
 * @date 2023/9/12 18:42
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    private final RabbitService rabbitService;

    private final OSSComponent ossComponent;

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

        // 文件上传
        String postUrl = ossComponent.fileUpLoad(publishPostParam.getFile());

        // 构造消息
        Post post = new Post();
        post.setUuid(user.getUuid());
        post.setTitle(publishPostParam.getTitle());
        post.setPostStatus(PostStatus.TO_CHECK);
        post.setOriginUrl(postUrl);

        // 发布到MQ中
        rabbitService.sendMessage(MQConst.EXCHANGE_POST_DIRECT, MQConst.ROUTING_PUBLISH_POST, post);
    }
}
