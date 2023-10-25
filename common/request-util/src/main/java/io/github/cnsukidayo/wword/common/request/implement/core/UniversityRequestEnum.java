package io.github.cnsukidayo.wword.common.request.implement.core;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.core.UniversityRequest;
import io.github.cnsukidayo.wword.model.dto.UniversityDTO;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import okhttp3.Request;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/10/25 22:08
 */
public enum UniversityRequestEnum implements UniversityRequest {
    DEFAULT_IMPLEMENT;

    @Override
    public ResponseWrapper<BaseResponse<List<UniversityDTO>>> getByName(String schoolName) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/university/getByName")
                .setPathVariable(schoolName)
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }
}
