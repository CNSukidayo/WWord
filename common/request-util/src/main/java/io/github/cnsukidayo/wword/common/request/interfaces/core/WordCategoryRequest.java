package io.github.cnsukidayo.wword.common.request.interfaces.core;

import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.dto.WordCategoryDTO;
import io.github.cnsukidayo.wword.model.params.WordCategoryParam;
import io.github.cnsukidayo.wword.model.params.WordCategoryWordParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.WordCategoryDetailVO;

import java.util.List;

/**
 * 单词收藏管理接口
 *
 * @author sukidayo
 * @date 2024/2/16 15:45
 */
public interface WordCategoryRequest {

    /**
     * 创建一个单词收藏夹
     *
     * @param wordCategoryParam 单词收藏夹参数
     * @return 返回窗口成功的单词收藏夹信息
     */
    ResponseWrapper<BaseResponse<WordCategoryDTO>> save(WordCategoryParam wordCategoryParam);

    /**
     * 删除某个单词收藏夹
     *
     * @param id 单词收藏夹的id
     */
    ResponseWrapper<BaseResponse<Void>> remove(Long id);

    /**
     * 更新单词收藏夹列表的信息;可以更新顺序/收藏夹的信息
     *
     * @param addWordCategoryParams 单词收藏夹列表参数
     */
    ResponseWrapper<BaseResponse<Void>> update(List<WordCategoryParam> addWordCategoryParams);

    /**
     * 查询当前用户的所有的单词收藏夹
     *
     * @return 返回收藏夹的详情信息列表
     */
    ResponseWrapper<BaseResponse<List<WordCategoryDetailVO>>> list();

    /**
     * 添加单词到收藏夹中
     *
     * @param wordCategoryId 目标收藏夹的id
     * @param wordId         目标单词的id
     */
    ResponseWrapper<BaseResponse<Void>> addWord(Long wordCategoryId, Long wordId);

    /**
     * 移除收藏夹中的某个单词
     *
     * @param wordCategoryId 单词收藏夹的id
     * @param wordId         单词的id
     */
    ResponseWrapper<BaseResponse<Void>> removeWord(Long wordCategoryId, Long wordId);

    /**
     * 更改单词在收藏夹中的顺序
     *
     * @param wordCategoryWordList 单词参数信息
     */
    ResponseWrapper<BaseResponse<Void>> updateWordOrder(List<WordCategoryWordParam> wordCategoryWordList);

}
