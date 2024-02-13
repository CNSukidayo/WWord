package io.github.cnsukidayo.wword.common.request.interfaces.core;

import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.enums.SystemInfoType;
import io.github.cnsukidayo.wword.model.support.BaseResponse;

/**
 * @author sukidayo
 * @date 2024/2/10 10:30
 */
public interface SystemInfoRequest {
    /**
     * 获取系统信息,返回markdown格式的信息
     *
     * @param systemInfoType 信息的类型
     * @return 返回信息
     */
    ResponseWrapper<BaseResponse<String>> getSystemInfo(SystemInfoType systemInfoType);
}
