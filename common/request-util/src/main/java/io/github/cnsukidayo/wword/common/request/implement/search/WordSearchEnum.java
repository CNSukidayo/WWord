package io.github.cnsukidayo.wword.common.request.implement.search;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.search.WordSearchRequest;
import io.github.cnsukidayo.wword.model.dto.es.WordESDTO;
import io.github.cnsukidayo.wword.model.dto.support.DataPage;
import io.github.cnsukidayo.wword.model.params.SearchWordParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import okhttp3.Request;

/**
 * @author sukidayo
 * @date 2023/10/25 16:44
 */
public enum WordSearchEnum implements WordSearchRequest {
    DEFAULT_IMPLEMENT;

    @Override
    public ResponseWrapper<BaseResponse<DataPage<WordESDTO>>> searchWord(SearchWordParam searchWordParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/es/word/searchWord/").build())
            .post(requestHandler.jsonBody(searchWordParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request){
        };
    }
}
