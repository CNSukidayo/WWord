package io.github.cnsukidayo.wword.common.request.interfaces.auth;

import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.dto.UserProfileDTO;
import io.github.cnsukidayo.wword.model.params.LoginParam;
import io.github.cnsukidayo.wword.model.params.UpdatePasswordParam;
import io.github.cnsukidayo.wword.model.params.UpdateUserParam;
import io.github.cnsukidayo.wword.model.params.UserRegisterParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.token.AuthToken;

/**
 * 用户相关请求
 *
 * @author sukidayo
 * @date 2023/7/22 22:49
 */
public interface UserRequest {

    /**
     * 用户注册
     *
     * @param userRegisterParam 用户注册参数不为null
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> register(UserRegisterParam userRegisterParam);

    /**
     * 用户登录
     *
     * @param loginParam 登陆参数不为null
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<AuthToken>> login(LoginParam loginParam);

    /**
     * 得到用户的个人信息
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<UserProfileDTO>> getProfile();

    /**
     * 刷新token
     *
     * @param refreshToken 刷新token,可以从
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<AuthToken>> refresh(String refreshToken);

    /**
     * 更新密码
     *
     * @param updatePasswordParam 密码参数不为null
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> updatePassword(UpdatePasswordParam updatePasswordParam);

    /**
     * 更新个人信息
     *
     * @param updateUserParam 更新参数不为null
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> update(UpdateUserParam updateUserParam);

    /**
     * 退出登陆
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> logout();


}
