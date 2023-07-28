package io.github.cnsukidayo.wword.common.security.authentication;


import io.github.cnsukidayo.wword.model.pojo.User;

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
