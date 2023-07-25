package io.github.cnsukidayo.wword.exception;

/**
 * @author sukidayo
 * @date 2023/7/25 14:21
 */
public class AlreadyExistsException extends BadRequestException {

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
