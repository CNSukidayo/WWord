package cnsukidayo.com.gitee.security.core;

import cnsukidayo.com.gitee.exception.AbstractWWordException;
import cnsukidayo.com.gitee.model.support.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * controller层的异常处理器
 *
 * @author cnsukidayo
 */
@RestControllerAdvice(value = {"cnsukidayo.com.gitee.controller.content",
        "cnsukidayo.com.gitee.controller.authentication"})
public class ControllerExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(AbstractWWordException.class)
    public ResponseEntity<BaseResponse<?>> handleHaloException(AbstractWWordException e) {
        BaseResponse<Object> baseResponse = handleBaseException(e);
        baseResponse.setStatus(e.getStatus().value());
        baseResponse.setData(e.getErrorData());
        return new ResponseEntity<>(baseResponse, e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleGlobalException(Exception e) {
        BaseResponse<?> baseResponse = handleBaseException(e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        baseResponse.setStatus(status.value());
        baseResponse.setMessage(status.getReasonPhrase());
        return baseResponse;
    }

    private <T> BaseResponse<T> handleBaseException(Throwable t) {
        Assert.notNull(t, "Throwable must not be null");

        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setMessage(t.getMessage());

        log.error("Captured an exception:", t);

        return baseResponse;
    }

}

