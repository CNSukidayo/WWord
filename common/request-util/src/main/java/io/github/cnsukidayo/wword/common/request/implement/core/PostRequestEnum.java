package io.github.cnsukidayo.wword.common.request.implement.core;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.core.PostRequest;
import io.github.cnsukidayo.wword.model.param.PublishPostParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.PostAbstractVO;
import io.github.cnsukidayo.wword.model.vo.PostDetailVo;
import okhttp3.Request;

import java.util.HashMap;
import java.util.List;

/**
 * @author sukidayo
 * @date 2023/10/25 21:33
 */
public enum PostRequestEnum implements PostRequest {
    DEFAULT_IMPLEMENT;

    @Override
    public ResponseWrapper<BaseResponse<Void>> publishPost(PublishPostParam publishPostParam) {
        return null;
    }

    @Override
    public ResponseWrapper<BaseResponse<PostDetailVo>> getPostDetailUncheck(Long id) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/u/post/uncheck/getPostDetail")
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
    public ResponseWrapper<BaseResponse<List<PostAbstractVO>>> getPostListUncheck() {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("/api/u/post/uncheck/getPostList").build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }
}
