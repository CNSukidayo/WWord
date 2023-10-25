package io.github.cnsukidayo.wword.common.request.implement.auth;

import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.auth.UserRequest;
import io.github.cnsukidayo.wword.model.dto.UserProfileDTO;
import io.github.cnsukidayo.wword.model.params.LoginParam;
import io.github.cnsukidayo.wword.model.params.UpdatePasswordParam;
import io.github.cnsukidayo.wword.model.params.UpdateUserParam;
import io.github.cnsukidayo.wword.model.params.UserRegisterParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.token.AuthToken;
import okhttp3.Request;

/**
 * 单利<br>
 * 用户管理接口
 *
 * @author sukidayo
 * @date 2023/10/25 13:01
 */
public enum UserRequestEnum implements UserRequest {

    DEFAULT_IMPLEMENT;

    @Override
    public ResponseWrapper<BaseResponse<Void>> register(UserRegisterParam userRegisterParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/auth/user/register").build())
            .post(requestHandler.jsonBody(userRegisterParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<AuthToken>> login(LoginParam loginParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/auth/user/login").build())
            .post(requestHandler.jsonBody(loginParam))
            .build();
        return new ResponseWrapper<BaseResponse<AuthToken>>(requestHandler, request) {
        }
            .success(authTokenBaseResponse -> {
                AuthToken authToken = authTokenBaseResponse.getData();
                RequestRegister.setAuthToken(authToken);
            });
    }

    @Override
    public ResponseWrapper<BaseResponse<UserProfileDTO>> getProfile() {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/auth/user/getProfile").build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> updatePassword(UpdatePasswordParam updatePasswordParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/auth/user/updatePassword/").build())
            .post(requestHandler.jsonBody(updatePasswordParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> update(UpdateUserParam updateUserParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/auth/user/update/").build())
            .post(requestHandler.jsonBody(updateUserParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<AuthToken>> refresh(String refreshToken) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/auth/user/refresh/")
                .setPathVariable(refreshToken)
                .build())
            .post(requestHandler.jsonBody(RequestHandler.EMPTY_BODY))
            .build();
        return new ResponseWrapper<BaseResponse<AuthToken>>(requestHandler, request) {
        }
            .success(authTokenBaseResponse -> {
                AuthToken authToken = authTokenBaseResponse.getData();
                RequestRegister.setAuthToken(authToken);
            })
            .fail(errorVoBaseResponse -> requestHandler.refreshTokenFailHandler());
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> logout() {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/auth/user/logout/").build())
            .post(requestHandler.jsonBody(RequestHandler.EMPTY_BODY))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }


}
