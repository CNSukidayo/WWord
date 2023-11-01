package io.github.cnsukidayo.wword.common.request.implement.core;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.core.WordStructureRequest;
import io.github.cnsukidayo.wword.model.entity.WordStructure;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import okhttp3.Request;

import java.util.HashMap;
import java.util.List;

/**
 * @author sukidayo
 * @date 2023/10/25 17:09
 */
public enum WordStructureRequestEnum implements WordStructureRequest {
    DEFAULT_IMPLEMENT;


    @Override
    public ResponseWrapper<BaseResponse<List<WordStructure>>> selectWordStructureById(String languageId) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/word/wordStructure/selectWordById")
                .setRequestParams(new HashMap<>() {{
                    put("languageId", languageId);
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }
}
