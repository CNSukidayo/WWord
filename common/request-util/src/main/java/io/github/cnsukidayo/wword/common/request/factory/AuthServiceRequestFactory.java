package io.github.cnsukidayo.wword.common.request.factory;

import io.github.cnsukidayo.wword.common.request.implement.auth.LoggerRequestEnum;
import io.github.cnsukidayo.wword.common.request.implement.auth.PermissionRequestEnum;
import io.github.cnsukidayo.wword.common.request.implement.auth.RolePermissionRequestEnum;
import io.github.cnsukidayo.wword.common.request.implement.auth.UserRequestEnum;
import io.github.cnsukidayo.wword.common.request.interfaces.auth.LoggerRequest;
import io.github.cnsukidayo.wword.common.request.interfaces.auth.PermissionRequest;
import io.github.cnsukidayo.wword.common.request.interfaces.auth.RolePermissionRequest;
import io.github.cnsukidayo.wword.common.request.interfaces.auth.UserRequest;

/**
 * 权限模块的请求类单利工厂
 *
 * @author sukidayo
 * @date 2023/10/25 12:56
 */
public class AuthServiceRequestFactory {

    private static class AuthServiceRequestFactoryHandler {
        private static final AuthServiceRequestFactory REQUEST_FACTORY = new AuthServiceRequestFactory();
    }

    private AuthServiceRequestFactory() {
    }

    public static AuthServiceRequestFactory getInstance() {
        return AuthServiceRequestFactoryHandler.REQUEST_FACTORY;
    }

    /**
     * 用户请求的request
     *
     * @return 返回值不为null
     */
    public UserRequest userRequest() {
        return UserRequestEnum.DEFAULT_IMPLEMENT;
    }

    /**
     * 权限管理接口请求对象
     *
     * @return 返回值不为null
     */
    public PermissionRequest permissionRequest() {
        return PermissionRequestEnum.DEFAULT_IMPLEMENT;
    }

    /**
     * 角色权限管理接口
     *
     * @return 返回值不为null
     */
    public RolePermissionRequest rolePermissionRequest() {
        return RolePermissionRequestEnum.DEFAULT_IMPLEMENT;
    }

    /**
     * 日志记录接口
     *
     * @return 返回值不为null
     */
    public LoggerRequest loggerRequest() {
        return LoggerRequestEnum.DEFAULT_IMPLEMENT;
    }


}
