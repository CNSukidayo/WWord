package io.github.cnsukidayo.wword.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.entity.Word;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author sukidayo
 * @date 2023/9/10 14:32
 */
@Repository
public interface WordMapper extends BaseMapper<Word> {
    /**
     * 查询一个单词的结构数量(方便排序)
     *
     * @param wordId 单词id不为null
     * @return 返回数量不为null
     */
    Long countStructure(@Param("wordId") Long wordId);
}
