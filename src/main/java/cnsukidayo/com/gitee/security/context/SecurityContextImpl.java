package cnsukidayo.com.gitee.security.context;


import cnsukidayo.com.gitee.security.authentication.Authentication;

/**
 * Security context implementation.
 *
 * @author johnniang
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