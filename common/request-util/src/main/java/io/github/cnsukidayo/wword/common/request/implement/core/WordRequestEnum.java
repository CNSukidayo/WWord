package io.github.cnsukidayo.wword.common.request.implement.core;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.core.WordRequest;
import io.github.cnsukidayo.wword.model.dto.WordDTO;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import okhttp3.Request;

import java.util.HashMap;
import java.util.List;

/**
 * @author sukidayo
 * @date 2023/10/25 17:09
 */
public enum WordRequestEnum implements WordRequest {
    DEFAULT_IMPLEMENT;


    @Override
    public ResponseWrapper<BaseResponse<List<WordDTO>>> selectWordById(Long wordId) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/u/word/selectWordById")
                .setRequestParams(new HashMap<>() {{
                    put("wordId", String.valueOf(wordId));
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<List<WordDTO>>> batchSelectWordById(List<Long> wordIds) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/u/word/batchSelectWordById").build())
            .post(requestHandler.jsonBody(wordIds))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }
}
