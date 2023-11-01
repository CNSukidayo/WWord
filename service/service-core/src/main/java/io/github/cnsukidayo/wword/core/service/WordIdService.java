package io.github.cnsukidayo.wword.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * 判断一个单词是否在一个<strong>父</strong>划分中
     *
     * @param word     单词内容不为null
     * @param divideId 划分id不为null
     * @return 返回值不为null
     */
    Boolean exist(String word, Long divideId);
}
