package io.github.cnsukidayo.wword.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.Word;
import io.github.cnsukidayo.wword.model.entity.WordId;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/9/10 14:23
 */
public interface WordIdService extends IService<WordId> {

    /**
     * 查询某个父划分下的有哪些单词
     *
     * @param divideId 父划分的id(所谓父划分就是在divide表中parent_id为-1的划分)
     * @return 返回的集合不为null
     */
    List<WordId> selectWordIdByDivideId(Long divideId);

    /**
     * 查询比当前划分大的并且单词相同的WordId
     *
     * @param wordId wordId必须不为null
     * @return 返回集合不为null
     */
    List<WordId> selectSameWordIdWord(WordId wordId);

    /**
     * 根据单词的id查询单词的详细信息
     *
     * @param wordId 单词id不为null
     * @return 返回的集合不为null
     */
    List<Word> selectWordById(Long wordId);

    /**
     * 添加一个单词
     *
     * @param word 待添加的单词不为null
     * @return 返回插入后的结果不为null
     */
    Word saveWord(Word word);

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
     * 判断一个单词是否在一个划分中
     *
     * @param word     单词内容不为null
     * @param divideId 划分id不为null
     * @return 返回值不为null
     */
    Boolean exist(String word, Long divideId);
}
