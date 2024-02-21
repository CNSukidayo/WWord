package io.github.cnsukidayo.wword.common.request.interfaces.core;

import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.param.PublishPostParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.PostAbstractVO;
import io.github.cnsukidayo.wword.model.vo.PostDetailVo;

import java.util.List;

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

    /**
     * 根据帖子的Id获取帖子的详细
     *
     * @param id 帖子的id
     * @return 返回帖子的详情信息
     */
    ResponseWrapper<BaseResponse<PostDetailVo>> getPostDetailUncheck(Long id);

    /**
     * 随机获取帖子列表
     *
     * @return 返回帖子的摘要信息
     */
    ResponseWrapper<BaseResponse<List<PostAbstractVO>>> getPostListUncheck();


}
