package io.github.cnsukidayo.wword.common.security.context;

import io.github.cnsukidayo.wword.common.security.authentication.Authentication;
import org.springframework.lang.Nullable;

/**
 * Security context interface.
 *
 * @author cnsukidayo
 */
public interface SecurityContext {

    /**
     * Gets the currently authenticated principal.
     *
     * @return the Authentication or null if authentication information is unavailable
     */
    @Nullable
    Authentication getAuthentication();

    /**
     * Changes the currently authenticated principal, or removes the authentication information.
     *
     * @param authentication the new authentication or null if no further authentication should
     * not be stored
     */
    void setAuthentication(@Nullable Authentication authentication);

    /**
     * Check if the current context has authenticated or not.
     *
     * @return true if authenticate; false otherwise
     */
    default boolean isAuthenticated() {
        return getAuthentication() != null;
    }
}
