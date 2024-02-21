package io.github.cnsukidayo.wword.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.Word;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/10/31 16:00
 */
public interface WordService extends IService<Word> {
    /**
     * 查询一个单词的结构数量(方便排序)
     *
     * @param wordId 单词id不为null
     * @return 返回数量不为null
     */
    Long countStructure(Long wordId);

    /**
     * 查询一个单词的信息数
     *
     * @param wordId 单词id不为null
     * @return 返回数量不为null
     */
    Long countValue(Long wordId);

    /**
     * 添加一个单词
     *
     * @param word 待添加的单词不为null
     * @return 返回插入后的结果不为null
     */
    Word saveWord(Word word);

    /**
     * 根据单词的id查询单词的详细信息
     *
     * @param wordId 单词id不为null
     * @return 返回的集合不为null
     */
    List<Word> selectWordById(Long wordId);

    /**
     * 批量查询单词详情信息
     *
     * @param wordIds 单词详情列表
     * @return 返回所有单词信息
     */
    List<Word> batchSelectWordById(List<Long> wordIds);
}
