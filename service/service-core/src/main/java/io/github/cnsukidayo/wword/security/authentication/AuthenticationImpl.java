package io.github.cnsukidayo.wword.security.authentication;


import io.github.cnsukidayo.wword.pojo.User;

/**
 * Authentication implementation.
 *
 * @author cnsukidayo
 */
public record AuthenticationImpl(User user) implements Authentication {

}
