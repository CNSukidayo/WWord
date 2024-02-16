package io.github.cnsukidayo.wword.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.WordCategory;
import io.github.cnsukidayo.wword.model.entity.WordCategoryWord;
import io.github.cnsukidayo.wword.model.vo.WordCategoryDetailVO;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/27 10:43
 */
public interface WordCategoryService extends IService<WordCategory> {

    /**
     * 创建一个单词收藏夹
     *
     * @param wordCategoryParam 收藏夹参数
     * @param uuid              用户id
     */
    WordCategory save(WordCategory wordCategoryParam, Long uuid);

    /**
     * 删除某个单词收藏夹
     *
     * @param id 收藏夹id
     */
    void remove(Long id, Long uuid);

    /**
     * 更新收藏夹信息
     */
    void update(List<WordCategory> addWordCategoryParams, Long uuid);

    /**
     * 查询当前用户的所有的单词收藏夹
     *
     * @param uuid 用户id
     */
    List<WordCategoryDetailVO> getWordCategoryListAndDetail(Long uuid);

    /**
     * 添加单词到收藏夹中
     *
     * @param wordId         单词的id
     * @param wordCategoryId 收藏夹的id
     */
    void addWord(Long wordCategoryId, Long wordId, Long uuid);

    /**
     * 移除收藏夹中的某个单词
     *
     * @param wordCategoryId 收藏夹的id
     * @param wordId         单词的id
     */
    void removeWord(Long wordCategoryId, Long wordId, Long uuid);

    /**
     * 更改单词在收藏夹中的顺序
     *
     * @param wordCategoryWordList 单词列表
     */
    void updateWordOrder(List<WordCategoryWord> wordCategoryWordList, Long uuid);

}
