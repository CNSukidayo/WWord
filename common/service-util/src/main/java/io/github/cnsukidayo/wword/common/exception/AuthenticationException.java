package io.github.cnsukidayo.wword.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Authentication exception.
 *
 * @author cnsukidayo
 */
public class AuthenticationException extends AbstractWWordException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}