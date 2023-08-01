package io.github.cnsukidayo.wword.common.security.authentication;


import io.github.cnsukidayo.wword.model.entity.User;

/**
 * Authentication implementation.
 *
 * @author cnsukidayo
 */
public record AuthenticationImpl(User user) implements Authentication {

}
