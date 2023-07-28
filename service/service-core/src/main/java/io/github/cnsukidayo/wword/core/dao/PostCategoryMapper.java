package io.github.cnsukidayo.wword.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.pojo.PostCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/27 10:44
 */
@Repository
public interface PostCategoryMapper extends BaseMapper<PostCategory> {
    /**
     * 添加一条点赞记录<br>
     * 点赞之前必须先判断目标收藏夹是不是链接收藏夹
     *
     * @param postCategoryId 收藏夹id不能为null
     * @param uuid           用户uuid不能为null
     */
    Long insertLike(@Param("postCategoryId") Long postCategoryId, @Param("uuid") Long uuid);

    /**
     * 删除一条点赞记录
     *
     * @param postCategoryId 收藏夹id不能为nul
     * @param uuid           用户uuid不能为null
     */
    Long deleteLike(@Param("postCategoryId") Long postCategoryId, @Param("uuid") Long uuid);

    /**
     * 查询某个收藏夹的点赞数量
     *
     * @param id 收藏夹id不能为nul
     */
    Long likeCount(@Param("postCategoryId") Long postCategoryId);

    /**
     * 查询所有目标帖子收藏夹
     *
     * @param categoryIdList 所有引用收藏夹的id,参数不为null
     * @return 返回所有真实的集合
     */
    List<PostCategory> selectTargetPostCategory(@Param("idList") List<Long> categoryIdList);
}
