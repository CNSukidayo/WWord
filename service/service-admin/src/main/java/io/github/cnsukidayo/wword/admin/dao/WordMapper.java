package io.github.cnsukidayo.wword.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.pojo.Word;
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
    void save(@Param("wordCollection") Collection<Word> wordCollection);

    /**
     * 插入单词id对照表
     *
     * @param word       单词内容
     * @param languageId 语种id
     * @param wordId     单词id
     */
    void saveWordId(@Param("word") String word, @Param("languageId") Long languageId, @Param("wordId") Long wordId);


}
