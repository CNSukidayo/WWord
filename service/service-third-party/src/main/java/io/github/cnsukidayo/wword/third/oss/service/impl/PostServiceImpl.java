package io.github.cnsukidayo.wword.third.oss.service.impl;

import io.github.cnsukidayo.wword.common.utils.ValidationUtils;
import io.github.cnsukidayo.wword.model.param.PublishPostParam;
import io.github.cnsukidayo.wword.mq.constant.MQConst;
import io.github.cnsukidayo.wword.mq.service.RabbitService;
import io.github.cnsukidayo.wword.third.oss.service.OSSService;
import io.github.cnsukidayo.wword.third.oss.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author sukidayo
 * @date 2023/9/14 9:43
 */
@Service
public class PostServiceImpl implements PostService {

    private final OSSService ossService;

    private final RabbitService rabbitService;

    public PostServiceImpl(OSSService ossService,
                           RabbitService rabbitService) {
        this.ossService = ossService;
        this.rabbitService = rabbitService;
    }

    @Override
    public void publishPost(PublishPostParam publishPostParam) {
        Assert.notNull(publishPostParam, "publishPostParam must not be null");
        // 参数校验
        ValidationUtils.validate(publishPostParam);

        // 远程调用
        String postUrl = ossService.fileUpLoad(publishPostParam.getFile());

        // 发布到MQ中
        rabbitService.sendMessage(MQConst.EXCHANGE_POST_DIRECT, MQConst.ROUTING_PUBLISH_POST, postUrl);
    }
}
