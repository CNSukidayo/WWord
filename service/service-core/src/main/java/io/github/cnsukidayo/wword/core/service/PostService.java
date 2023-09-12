package io.github.cnsukidayo.wword.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.Post;
import io.github.cnsukidayo.wword.model.param.PublishPostParam;

/**
 * @author sukidayo
 * @date 2023/9/12 18:42
 */
public interface PostService extends IService<Post> {

    /**
     * 发布一个帖子
     *
     * @param publishPostParam 发布一个帖子
     */
    void publishPost(PublishPostParam publishPostParam);
}
