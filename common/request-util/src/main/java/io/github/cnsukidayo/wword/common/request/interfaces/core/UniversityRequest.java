package io.github.cnsukidayo.wword.common.request.interfaces.core;

import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.dto.UniversityDTO;
import io.github.cnsukidayo.wword.model.support.BaseResponse;

import java.util.List;

/**
 * 学校管理接口
 *
 * @author sukidayo
 * @date 2023/10/25 22:04
 */
public interface UniversityRequest {

    /**
     * 根据名称查询学校
     *
     * @param schoolName 学校名称
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<List<UniversityDTO>>> getByName(String schoolName);


}
