package io.github.cnsukidayo.wword.exception;

/**
 * @author sukidayo
 * @date 2023/7/25 14:21
 */
public class NonExistsException extends BadRequestException {

    public NonExistsException(String message) {
        super(message);
    }

    public NonExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
