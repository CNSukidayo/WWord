package cnsukidayo.com.gitee.security.authentication;


import io.github.cnsukidayo.wword.pojo.User;

/**
 * Authentication.
 *
 * @author cnsukidayo
 */
public interface Authentication {

    /**
     * Get user detail.
     *
     * @return user detail
     */
    User user();
}
