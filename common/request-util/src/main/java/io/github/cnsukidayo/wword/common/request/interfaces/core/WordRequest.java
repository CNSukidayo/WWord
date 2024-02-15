package io.github.cnsukidayo.wword.common.request.interfaces.core;

import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.dto.WordDTO;
import io.github.cnsukidayo.wword.model.support.BaseResponse;

import java.util.List;

/**
 * 划分列表管理接口
 *
 * @author sukidayo
 * @date 2023/10/25 17:01
 */
public interface WordRequest {

    /**
     * 根据一个单词的id查询出单词的详细信息
     *
     * @param wordId 单词的Id
     * @return 返回单词信息
     */
    ResponseWrapper<BaseResponse<List<WordDTO>>> selectWordById(Long wordId);

}
