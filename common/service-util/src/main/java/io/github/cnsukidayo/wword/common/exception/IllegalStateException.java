package io.github.cnsukidayo.wword.common.exception;

/**
 * @author sukidayo
 * @date 2023/8/1 10:33
 */
public class IllegalStateException extends BadRequestException{

    public IllegalStateException(String message) {
        super(message);
    }

    public IllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
