package io.github.cnsukidayo.wword.security.authentication;


import io.github.cnsukidayo.wword.model.pojo.User;

/**
 * Authentication implementation.
 *
 * @author cnsukidayo
 */
public record AuthenticationImpl(User user) implements Authentication {

}
