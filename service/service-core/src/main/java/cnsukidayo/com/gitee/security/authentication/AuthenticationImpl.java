package cnsukidayo.com.gitee.security.authentication;


import io.github.cnsukidayo.wword.pojo.User;

/**
 * Authentication implementation.
 *
 * @author cnsukidayo
 */
public record AuthenticationImpl(User user) implements Authentication {

}
