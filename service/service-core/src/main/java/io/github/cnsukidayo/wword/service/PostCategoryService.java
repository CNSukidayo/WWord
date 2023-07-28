package io.github.cnsukidayo.wword.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.enums.CategoryAttribute;
import io.github.cnsukidayo.wword.model.params.AddPostCategoryParam;
import io.github.cnsukidayo.wword.model.params.UpdatePostCategoryParam;
import io.github.cnsukidayo.wword.model.pojo.PostCategory;
import io.github.cnsukidayo.wword.model.vo.PostCategoryVO;

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
     * @see CategoryAttribute
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
     * 用户只能删除自已的收藏夹<br>
     * 该方法同样可用作取消收藏一个收藏夹,即{@link PostCategoryService#star(Long, Long)}方法的逆操作.
     *
     * @param id   收藏夹id 参数不为null
     * @param uuid 用户id 参数不为null
     */
    void removeById(Long id, Long uuid);

    /**
     * 点赞收藏夹
     *
     * @param id   收藏夹id不为null
     * @param uuid 用户id不为null
     * @return 是否点赞成功
     */

    Boolean like(Long id, Long uuid);

    /**
     * 取消点赞
     *
     * @param id   收藏夹id不为null
     * @param uuid 用户id不为null
     * @return 是否取消点赞成功
     */
    Boolean dislike(Long id, Long uuid);

    /**
     * 查询某个收藏夹的详细信息
     *
     * @param id   收藏夹id不为null
     * @param uuid 用户id不为null
     * @return 返回收藏夹内的详细信息
     */
    PostCategoryVO getById(Long id, Long uuid);

    /**
     * 收藏某个收藏夹
     *
     * @param id   收藏夹id不为null
     * @param uuid 用户id不为null
     */
    void star(Long id, Long uuid);
}
