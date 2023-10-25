package io.github.cnsukidayo.wword.common.request.implement.auth;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.auth.LoggerRequest;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.LoginLogVO;
import okhttp3.Request;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/10/25 21:13
 */
public enum LoggerRequestEnum implements LoggerRequest {
    DEFAULT_IMPLEMENT;

    @Override
    public ResponseWrapper<BaseResponse<List<LoginLogVO>>> getLoginLog() {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/auth/logger/getLoginLog/")
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }
}
