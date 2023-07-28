package io.github.cnsukidayo.wword.common.security.context;


import io.github.cnsukidayo.wword.common.security.authentication.Authentication;

/**
 * Security context implementation.
 *
 * @author cnsukidayo
 */
public class SecurityContextImpl implements SecurityContext {

    private Authentication authentication;

    public SecurityContextImpl(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public Authentication getAuthentication() {
        return authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

}
