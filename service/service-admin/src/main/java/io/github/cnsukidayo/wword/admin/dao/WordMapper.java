package io.github.cnsukidayo.wword.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.entity.Word;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author sukidayo
 * @date 2023/7/31 14:01
 */
@Repository
public interface WordMapper extends BaseMapper<Word> {

    /**
     * 批量插入单词
     *
     * @param wordCollection 单词集合不为null
     */
    void replaceWord(@Param("wordCollection") Collection<Word> wordCollection);

}
