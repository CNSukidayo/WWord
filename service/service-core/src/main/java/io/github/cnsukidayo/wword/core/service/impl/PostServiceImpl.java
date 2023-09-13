package io.github.cnsukidayo.wword.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.common.utils.ValidationUtils;
import io.github.cnsukidayo.wword.core.dao.PostMapper;
import io.github.cnsukidayo.wword.core.service.PostService;
import io.github.cnsukidayo.wword.model.entity.Post;
import io.github.cnsukidayo.wword.model.param.PublishPostParam;
import io.github.cnsukidayo.wword.third.oss.client.OSSFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author sukidayo
 * @date 2023/9/12 18:42
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    private final PostMapper postMapper;

    private final OSSFeignClient ossFeignClient;

    public PostServiceImpl(PostMapper postMapper,
                           OSSFeignClient ossFeignClient) {
        this.postMapper = postMapper;
        this.ossFeignClient = ossFeignClient;
    }

    @Override
    public void publishPost(PublishPostParam publishPostParam) {
        Assert.notNull(publishPostParam, "publishPostParam must not be null");
        // 参数校验
        ValidationUtils.validate(publishPostParam);

        // 远程调用
//        String resultUrl = ossFeignClient.fileUpLoad(publishPostParam.getFile());

        // 发布到MQ中

    }
}
