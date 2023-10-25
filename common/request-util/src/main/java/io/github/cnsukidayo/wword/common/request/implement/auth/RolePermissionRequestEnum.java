package io.github.cnsukidayo.wword.common.request.implement.auth;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.common.request.interfaces.auth.RolePermissionRequest;
import io.github.cnsukidayo.wword.model.dto.RoleDTO;
import io.github.cnsukidayo.wword.model.params.PageQueryParam;
import io.github.cnsukidayo.wword.model.params.RoleParam;
import io.github.cnsukidayo.wword.model.params.RolePermissionParam;
import io.github.cnsukidayo.wword.model.params.UserRoleParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import okhttp3.Request;

import java.util.HashMap;

/**
 * @author sukidayo
 * @date 2023/10/25 20:49
 */
public enum RolePermissionRequestEnum implements RolePermissionRequest {
    DEFAULT_IMPLEMENT;

    @Override
    public ResponseWrapper<BaseResponse<Void>> addRole(RoleParam roleParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/auth/role_permission/save_role/").build())
            .post(requestHandler.jsonBody(roleParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> updateRole(String roleId, RoleParam roleParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/auth/role_permission/update_role/")
                .setPathVariable(roleId)
                .build())
            .post(requestHandler.jsonBody(roleParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> removeRole(String roleId) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/auth/role_permission/remove_role/")
                .setRequestParams(new HashMap<>() {{
                    put("roleId", roleId);
                }})
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<IPage<RoleDTO>>> rolePage(PageQueryParam pageQueryParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/auth/role_permission/role_page/")
                .build())
            .post(requestHandler.jsonBody(pageQueryParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> grantPermission(RolePermissionParam rolePermissionParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/auth/role_permission/grant_permission/")
                .build())
            .post(requestHandler.jsonBody(rolePermissionParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> revokePermission(String roleId) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/auth/role_permission/revoke_permission/")
                .setPathVariable(roleId)
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> rolePermissionPage(String roleId, PageQueryParam pageQueryParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/auth/role_permission/role_permission_page/")
                .setPathVariable(roleId)
                .build())
            .post(requestHandler.jsonBody(pageQueryParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> grantRole(UserRoleParam userRoleParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/auth/role_permission/grant_role/")
                .build())
            .post(requestHandler.jsonBody(userRoleParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> revokeRole(String uuid) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/auth/role_permission/revoke_role/")
                .setPathVariable(uuid)
                .build())
            .get()
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<IPage<RoleDTO>>> userRolePage(String uuid, PageQueryParam pageQueryParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/auth/role_permission/user_role_page/")
                .setPathVariable(uuid)
                .build())
            .post(requestHandler.jsonBody(pageQueryParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }

    @Override
    public ResponseWrapper<BaseResponse<Void>> cloneBatch(UserRoleParam userRoleParam) {
        RequestHandler requestHandler = RequestRegister.getRequestHandler();
        Request request = new Request.Builder()
            .url(requestHandler
                .createPrefixUrl("api/auth/role_permission/clone_batch/")
                .build())
            .post(requestHandler.jsonBody(userRoleParam))
            .build();
        return new ResponseWrapper<>(requestHandler, request) {
        };
    }
}
