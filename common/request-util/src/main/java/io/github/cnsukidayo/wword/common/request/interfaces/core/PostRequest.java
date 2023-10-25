package io.github.cnsukidayo.wword.common.request.interfaces.core;

import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.param.PublishPostParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;

/**
 * @author sukidayo
 * @date 2023/10/25 22:04
 */
public interface PostRequest {
    /**
     * 发布文章
     *
     * @param publishPostParam 发布帖子参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> publishPost(PublishPostParam publishPostParam);

}
