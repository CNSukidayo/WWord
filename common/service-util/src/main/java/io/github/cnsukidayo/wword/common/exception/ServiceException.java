package io.github.cnsukidayo.wword.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 服务器相关异常
 *
 * @author sukidayo
 * @date 2023/8/1 18:44
 */
public class ServiceException extends AbstractWWordException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
