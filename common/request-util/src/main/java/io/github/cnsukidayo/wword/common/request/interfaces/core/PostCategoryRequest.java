package io.github.cnsukidayo.wword.common.request.interfaces.core;

import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.dto.PostCategoryDTO;
import io.github.cnsukidayo.wword.model.params.AddPostCategoryParam;
import io.github.cnsukidayo.wword.model.params.UpdatePostCategoryParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.PostCategoryVO;

import java.util.List;

/**
 * 用户帖子收藏管理接口
 * @author sukidayo
 * @date 2023/10/25 21:18
 */
public interface PostCategoryRequest {

    /**
     * 添加一个收藏夹
     *
     * @param addPostCategoryParam 添加收藏夹参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> save(AddPostCategoryParam addPostCategoryParam);

    /**
     * 查询当前用户的所有收藏夹
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<List<PostCategoryDTO>>> list();

    /**
     * 根据id查询某个用户所有公开的帖子收藏夹
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<List<PostCategoryDTO>>> listPublic(String uuid);

    /**
     * 更新某个收藏夹的信息
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> update(UpdatePostCategoryParam updatePostCategoryParam);

    /**
     * 删除某个收藏夹&取消收藏某个收藏夹
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> remove(String id);

    /**
     * 点赞某个收藏夹
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Boolean>> like(String id);

    /**
     * 取消点赞某个收藏夹
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Boolean>> dislike(String id);

    /**
     * 查询某个收藏夹的详细信息
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<PostCategoryVO>> getById(String id);

    /**
     * 查询某个收藏夹的详细信息
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> star(String id);


}
