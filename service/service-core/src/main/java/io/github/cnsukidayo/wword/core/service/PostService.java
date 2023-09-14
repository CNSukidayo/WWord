package io.github.cnsukidayo.wword.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.Post;
import io.github.cnsukidayo.wword.model.entity.User;
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
     * @param user 发布帖子的用户
     */
    void publishPost(PublishPostParam publishPostParam, User user);

}
