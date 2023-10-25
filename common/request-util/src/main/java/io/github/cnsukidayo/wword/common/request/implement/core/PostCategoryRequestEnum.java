package io.github.cnsukidayo.wword.common.request.implement.core;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.core.PostCategoryRequest;
import io.github.cnsukidayo.wword.model.dto.PostCategoryDTO;
import io.github.cnsukidayo.wword.model.params.AddPostCategoryParam;
import io.github.cnsukidayo.wword.model.params.UpdatePostCategoryParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.PostCategoryVO;
import okhttp3.Request;

import java.util.HashMap;
import java.util.List;

/**
 * @author sukidayo
 * @date 2023/10/25 21:33
 */
public enum PostCategoryRequestEnum implements PostCategoryRequest {
    DEFAULT_IMPLEMENT;

    @Override
    public ResponseWrapper<BaseResponse<Void>> save(AddPostCategoryParam addPostCategoryParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/u/post_category/save/").build())
            .post(requestHandler.jsonBody(addPostCategoryParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<List<PostCategoryDTO>>> list() {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/u/post_category/list/").build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<List<PostCategoryDTO>>> listPublic(String uuid) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/post_category/listPublic/")
                .setRequestParams(new HashMap<>() {{
                    put("UUID", uuid);
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> update(UpdatePostCategoryParam updatePostCategoryParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/post_category/listPublic/")
                .build())
            .post(requestHandler.jsonBody(updatePostCategoryParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> remove(String id) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/post_category/remove/")
                .setRequestParams(new HashMap<>() {{
                    put("id", id);
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Boolean>> like(String id) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/post_category/like/")
                .setRequestParams(new HashMap<>() {{
                    put("id", id);
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Boolean>> dislike(String id) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/post_category/dislike/")
                .setRequestParams(new HashMap<>() {{
                    put("id", id);
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<PostCategoryVO>> getById(String id) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/post_category/getById/")
                .setRequestParams(new HashMap<>() {{
                    put("id", id);
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> star(String id) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/u/post_category/star/")
                .setRequestParams(new HashMap<>() {{
                    put("id", id);
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }
}
