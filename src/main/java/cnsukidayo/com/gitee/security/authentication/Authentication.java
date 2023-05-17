package cnsukidayo.com.gitee.security.authentication;


import cnsukidayo.com.gitee.model.pojo.User;

/**
 * Authentication.
 *
 * @author johnniang
 */
public interface Authentication {

    /**
     * Get user detail.
     *
     * @return user detail
     */
    User getUser();
}
