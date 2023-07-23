package io.github.cnsukidayo.wword.service;

import io.github.cnsukidayo.wword.token.AuthToken;
import io.github.cnsukidayo.wword.params.LoginParam;
import io.github.cnsukidayo.wword.pojo.User;

/**
 * @author sukidayo
 * @date 2023/5/19 18:09
 */
public interface UserService {

    AuthToken refreshToken(String refreshToken);

    User getById(Integer id);

    AuthToken login(LoginParam loginParam);

    User getProfile();

    void clearToken();
}
