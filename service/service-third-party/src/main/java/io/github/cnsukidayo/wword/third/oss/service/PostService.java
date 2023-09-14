package io.github.cnsukidayo.wword.third.oss.service;

import io.github.cnsukidayo.wword.model.param.PublishPostParam;

/**
 * @author sukidayo
 * @date 2023/9/14 9:43
 */
public interface PostService {

    /**
     * 发布一个帖子
     *
     * @param publishPostParam 发布一个帖子
     */
    void publishPost(PublishPostParam publishPostParam);

}
