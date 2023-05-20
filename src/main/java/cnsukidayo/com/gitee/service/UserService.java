package cnsukidayo.com.gitee.service;

import cnsukidayo.com.gitee.model.params.LoginParam;
import cnsukidayo.com.gitee.model.pojo.User;
import cnsukidayo.com.gitee.security.token.AuthToken;

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
