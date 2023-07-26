package io.github.cnsukidayo.wword.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.params.LoginParam;
import io.github.cnsukidayo.wword.params.UpdatePasswordParam;
import io.github.cnsukidayo.wword.params.UpdateUserParam;
import io.github.cnsukidayo.wword.params.UserRegisterParam;
import io.github.cnsukidayo.wword.pojo.User;
import io.github.cnsukidayo.wword.token.AuthToken;

/**
 * @author sukidayo
 * @date 2023/5/19 18:09
 */
public interface UserService extends IService<User> {

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
