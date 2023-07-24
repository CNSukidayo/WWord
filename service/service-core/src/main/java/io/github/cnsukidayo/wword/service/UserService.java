package io.github.cnsukidayo.wword.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.params.LoginParam;
import io.github.cnsukidayo.wword.pojo.User;
import io.github.cnsukidayo.wword.token.AuthToken;

/**
 * @author sukidayo
 * @date 2023/5/19 18:09
 */
public interface UserService extends IService<User> {

    void register();

    AuthToken refreshToken(String refreshToken);

    User getById(Integer id);

    AuthToken login(LoginParam loginParam);

    User getProfile();


    void clearToken();
}
