package cnsukidayo.com.gitee.security.authentication;


import cnsukidayo.com.gitee.model.pojo.User;

/**
 * Authentication implementation.
 *
 * @author johnniang
 */
public class AuthenticationImpl implements Authentication {

    private final User user;

    public AuthenticationImpl(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }
}
