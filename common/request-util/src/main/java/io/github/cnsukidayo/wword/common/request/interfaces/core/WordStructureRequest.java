package io.github.cnsukidayo.wword.common.request.interfaces.core;

import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.dto.WordStructureDTO;
import io.github.cnsukidayo.wword.model.support.BaseResponse;

import java.util.List;

/**
 * 单词结构管理接口
 *
 * @author sukidayo
 * @date 2023/10/25 17:01
 */
public interface WordStructureRequest {
    /**
     * 格根据语种id获取单词的结构
     *
     * @param languageId 语种id不为null
     * @return 返回单词结构
     */
    ResponseWrapper<BaseResponse<List<WordStructureDTO>>> selectWordStructureById(String languageId);

}
