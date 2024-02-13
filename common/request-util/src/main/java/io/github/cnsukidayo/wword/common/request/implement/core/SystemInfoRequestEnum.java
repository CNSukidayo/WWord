package io.github.cnsukidayo.wword.common.request.implement.core;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.core.SystemInfoRequest;
import io.github.cnsukidayo.wword.model.enums.SystemInfoType;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import okhttp3.Request;

import java.util.HashMap;

/**
 * @author sukidayo
 * @date 2024/2/10 10:31
 */
public enum SystemInfoRequestEnum implements SystemInfoRequest {
    DEFAULT_IMPLEMENT;

    @Override
    public ResponseWrapper<BaseResponse<String>> getSystemInfo(SystemInfoType systemInfoType) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/u/systemInfo/uncheck/getMessage")
                .setRequestParams(new HashMap<>() {{
                    put("systemInfoType", systemInfoType.name());
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }
}
