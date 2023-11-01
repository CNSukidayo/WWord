package io.github.cnsukidayo.wword.common.request.implement.core;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.core.DivideRequest;
import io.github.cnsukidayo.wword.model.dto.DivideDTO;
import io.github.cnsukidayo.wword.model.dto.DivideWordDTO;
import io.github.cnsukidayo.wword.model.dto.LanguageClassDTO;
import io.github.cnsukidayo.wword.model.dto.WordDTO;
import io.github.cnsukidayo.wword.model.params.AddDivideParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import okhttp3.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sukidayo
 * @date 2023/10/25 17:09
 */
public enum DivideRequestEnum implements DivideRequest {
    DEFAULT_IMPLEMENT;

    @Override
    public ResponseWrapper<BaseResponse<List<LanguageClassDTO>>> listLanguage() {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/u/word_divide/listLanguage/").build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<List<DivideDTO>>> listDivide(String languageId, String uuid) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/word_divide/listDivide/")
                .setRequestParams(new HashMap<>() {{
                    put("languageId", languageId);
                    put("uuid", uuid);
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<List<DivideWordDTO>>> listDivideWord(List<Long> divideIds) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/word_divide/listWord/")
                .build())
            .post(requestHandler.jsonBody(divideIds))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Map<Long, List<WordDTO>>>> listWordByDivideId(List<Long> divideIds) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/word_divide/listWordByDivideId/")
                .build())
            .post(requestHandler.jsonBody(divideIds))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> saveDivide(AddDivideParam addDivideParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/word_divide/save/")
                .build())
            .post(requestHandler.jsonBody(addDivideParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> remove(String id) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/word_divide/remove/")
                .setRequestParams(new HashMap<>() {{
                    put("id", id);
                }})
                .build())
            .post(requestHandler.jsonBody(RequestHandler.EMPTY_BODY))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> saveDivideWord(String divideId, List<Long> wordIdList) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/word_divide/saveDivideWord/")
                .setRequestParams(new HashMap<>() {{
                    put("divideId", divideId);
                }})
                .build())
            .post(requestHandler.jsonBody(wordIdList))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> deleteDivideWord(String divideId, List<Long> wordIdList) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/word_divide/deleteDivideWord/")
                .setRequestParams(new HashMap<>() {{
                    put("divideId", divideId);
                }})
                .build())
            .post(requestHandler.jsonBody(wordIdList))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> copyDivide(String divideId) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/word_divide/copyDivide/")
                .build())
            .post(requestHandler.jsonBody(RequestHandler.EMPTY_BODY))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

}
