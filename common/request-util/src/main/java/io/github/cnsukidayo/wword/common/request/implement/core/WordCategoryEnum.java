package io.github.cnsukidayo.wword.common.request.implement.core;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.core.WordCategoryRequest;
import io.github.cnsukidayo.wword.model.dto.WordCategoryDTO;
import io.github.cnsukidayo.wword.model.params.WordCategoryParam;
import io.github.cnsukidayo.wword.model.params.WordCategoryWordParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.WordCategoryDetailVO;
import okhttp3.Request;

import java.util.HashMap;
import java.util.List;

/**
 * @author sukidayo
 * @date 2023/10/25 17:09
 */
public enum WordCategoryEnum implements WordCategoryRequest {
    DEFAULT_IMPLEMENT;

    @Override
    public ResponseWrapper<BaseResponse<WordCategoryDTO>> save(WordCategoryParam wordCategoryParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/u/wordCategory/save").build())
            .post(requestHandler.jsonBody(wordCategoryParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> remove(Long id) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/u/wordCategory/remove")
                .setRequestParams(new HashMap<>() {{
                    put("id", String.valueOf(id));
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> update(List<WordCategoryParam> addWordCategoryParams) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/u/wordCategory/update").build())
            .post(requestHandler.jsonBody(addWordCategoryParams))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<List<WordCategoryDetailVO>>> list() {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/u/wordCategory/list").build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> addWord(Long wordCategoryId, Long wordId) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/u/wordCategory/addWord")
                .setRequestParams(new HashMap<>() {{
                    put("wordCategoryId", String.valueOf(wordCategoryId));
                    put("wordId", String.valueOf(wordId));
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> removeWord(Long wordCategoryId, Long wordId) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/u/wordCategory/removeWord")
                .setRequestParams(new HashMap<>() {{
                    put("wordCategoryId", String.valueOf(wordCategoryId));
                    put("wordId", String.valueOf(wordId));
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> updateWordOrder(List<WordCategoryWordParam> wordCategoryWordList) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/u/wordCategory/updateWordOrder").build())
            .post(requestHandler.jsonBody(wordCategoryWordList))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }


}
