package io.github.cnsukidayo.wword.common.request.implement.auth;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.auth.PermissionRequest;
import io.github.cnsukidayo.wword.model.params.PermissionParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.PermissionVO;
import okhttp3.Request;

import java.util.List;
import java.util.Map;

/**
 * @author sukidayo
 * @date 2023/10/25 16:04
 */
public enum PermissionRequestEnum implements PermissionRequest {
    DEFAULT_IMPLEMENT;

    @Override
    public ResponseWrapper<BaseResponse<Void>> trace(PermissionParam permissionParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/auth/permission/trace/").build())
            .post(requestHandler.jsonBody(permissionParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Map<String, Map<String, List<PermissionVO>>>>> getTraceByGroup() {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/auth/permission/getTraceByGroup/").build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> untrace(List<Long> permissionIdList) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/auth/permission/untrace/").build())
            .post(requestHandler.jsonBody(permissionIdList))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> updateTrace(String permissionId, PermissionParam permissionParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/auth/permission/update_trace/")
                .setPathVariable(permissionId)
                .build())
            .post(requestHandler.jsonBody(permissionParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }
}
