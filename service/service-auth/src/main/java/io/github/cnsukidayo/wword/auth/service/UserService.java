package io.github.cnsukidayo.wword.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.bo.UserPermissionBO;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.params.*;
import io.github.cnsukidayo.wword.model.token.AuthToken;

/**
 * @author sukidayo
 * @date 2023/9/7 18:37
 */
public interface UserService extends IService<User> {

    /**
     * 检查一个用户是否当前请求的目标接口的权限,将目标用户和权限结果封装到<br>
     * {@link UserPermissionBO}对象中
     *
     * @param checkAuthParam 校验权限参数不为null
     * @return 返回值不为null
     */
    UserPermissionBO getAndAuth(CheckAuthParam checkAuthParam);

    /**
     * 用户注册
     *
     * @param userRegisterParam 必须不为空
     */
    void register(UserRegisterParam userRegisterParam);

    /**
     * 用户登录
     *
     * @param loginParam 必须不为空
     * @return token
     */
    AuthToken login(LoginParam loginParam);

    /**
     * 更新用户密码
     *
     * @param user                必须不为空
     * @param updatePasswordParam 必须不为空
     */
    void updatePassword(User user, UpdatePasswordParam updatePasswordParam);

    /**
     * 更新用户个人信息
     *
     * @param updateUserParam 必须不为空
     * @param user            必须不为空
     */
    void update(UpdateUserParam updateUserParam, User user);

    /**
     * 刷新token
     *
     * @param refreshToken 必须不为空
     * @return 新的token
     */
    AuthToken refreshToken(String refreshToken);

    /**
     * 清除token(退出登陆状态)
     */
    void clearToken();

}
