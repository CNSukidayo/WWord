package io.github.cnsukidayo.wword.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.enums.PublicNess;
import io.github.cnsukidayo.wword.model.params.AddPostCategoryParam;
import io.github.cnsukidayo.wword.model.params.UpdatePostCategoryParam;
import io.github.cnsukidayo.wword.model.pojo.PostCategory;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/27 10:43
 */
public interface PostCategoryService extends IService<PostCategory> {

    /**
     * 添加一个收藏夹
     *
     * @param addPostCategoryParam 参数不为空
     * @param UUID                 用户的id不为空
     */
    void save(AddPostCategoryParam addPostCategoryParam, Long UUID);

    /**
     * 获取当前登录用户的所有帖子收藏夹信息
     *
     * @param uuid 用户的UUID不为空
     * @return 返回用户所有帖子收藏夹集合
     */
    List<PostCategory> list(Long uuid);

    /**
     * 获取某个用户所有公开的帖子收藏夹信息,如果收藏夹是私密的,则结果不会返回私密收藏夹.<br>
     *
     * @param uuid 用户的UUID不为空
     * @return 返回用户所有帖子收藏夹集合
     * @see PublicNess
     */
    List<PostCategory> listPublic(Long uuid);

    /**
     * 更新某个用户的收藏夹信息,通过UpdatePostCategoryParam的id锁定唯一的收藏夹<br>
     * 通过uuid来校验该收藏夹是不是对应用户的收藏夹
     *
     * @param updatePostCategoryParam 更新参数不为空
     * @param uuid                    用户UUID不为空
     */
    void updateByUUID(UpdatePostCategoryParam updatePostCategoryParam, Long uuid);

    /**
     * 删除用户的收藏夹,不能只通过收藏夹的id去删除.<br>
     * 用户只能删除自已的收藏夹
     *
     * @param id   收藏夹id 参数不为null
     * @param uuid 用户id 参数不为null
     */
    void removeById(Long id, Long uuid);

    void like(Long id, Long uuid);
}
