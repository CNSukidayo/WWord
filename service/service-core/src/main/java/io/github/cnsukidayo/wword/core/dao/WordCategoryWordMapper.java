package io.github.cnsukidayo.wword.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.entity.WordCategoryWord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author sukidayo
 * @date 2023/7/27 10:44
 */
@Repository
public interface WordCategoryWordMapper extends BaseMapper<WordCategoryWord> {

    /**
     * 搜索当前某个搜藏夹中的最后一个单词
     *
     * @param wordCategoryId 收藏夹的id
     * @param uuid           用户id
     */
    WordCategoryWord findOrderByWordOrderDescLast(@Param("wordCategoryId") Long wordCategoryId);

}
