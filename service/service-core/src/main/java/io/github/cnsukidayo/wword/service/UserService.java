package io.github.cnsukidayo.wword.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.params.LoginParam;
import io.github.cnsukidayo.wword.params.UserRegisterParam;
import io.github.cnsukidayo.wword.pojo.User;
import io.github.cnsukidayo.wword.token.AuthToken;

/**
 * @author sukidayo
 * @date 2023/5/19 18:09
 */
public interface UserService extends IService<User> {

    void register(UserRegisterParam userRegisterParam);

    AuthToken refreshToken(String refreshToken);

    AuthToken login(LoginParam loginParam);

    void clearToken();
}
