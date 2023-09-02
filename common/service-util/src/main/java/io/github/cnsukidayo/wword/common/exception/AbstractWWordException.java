package io.github.cnsukidayo.wword.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 项目基本的异常
 *
 * @author cnsukidayo
 * @date 2023/5/19 18:11
 */
public abstract class AbstractWWordException extends RuntimeException {

    public AbstractWWordException(String message) {
        super(message);
    }

    public AbstractWWordException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Http 状态码
     *
     * @return {@link HttpStatus}
     */
    public abstract Integer getStatus();

}
