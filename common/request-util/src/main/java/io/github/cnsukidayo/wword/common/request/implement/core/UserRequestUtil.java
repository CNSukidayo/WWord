package io.github.cnsukidayo.wword.common.request.implement.core;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.dto.UserProfileDTO;
import io.github.cnsukidayo.wword.model.params.LoginParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.token.AuthToken;
import okhttp3.Request;

/**
 * 如果是void方法则返回response,否则就封装返回的对象
 *
 * @author sukidayo
 * @date 2023/7/24 11:00
 */
public class UserRequestUtil {

    public static ResponseWrapper<BaseResponse<AuthToken>> refresh(String refreshToken) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/u/user/refresh/" + refreshToken))
            .post(requestHandler.jsonBody(null))
            .build();
        return new ResponseWrapper<BaseResponse<AuthToken>>(requestHandler, request) {
        }.success(authTokenBaseResponse -> {
            AuthToken authToken = authTokenBaseResponse.getData();
            RequestRegister.setAuthToken(authToken);
        }).fail(errorVoBaseResponse -> requestHandler.refreshTokenFailHandler());
    }

    public static ResponseWrapper<BaseResponse<AuthToken>> login(LoginParam loginParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/u/user/login"))
            .post(requestHandler.jsonBody(loginParam))
            .build();
        return new ResponseWrapper<BaseResponse<AuthToken>>(requestHandler, request) {
        }.success(authTokenBaseResponse -> {
            AuthToken authToken = authTokenBaseResponse.getData();
            RequestRegister.setAuthToken(authToken);
        });
    }

    public static ResponseWrapper<BaseResponse<UserProfileDTO>> getProfile() {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/u/user/getProfile"))
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {

        };
    }


}
