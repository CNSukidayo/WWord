package cnsukidayo.com.gitee.security.core;

import cnsukidayo.com.gitee.exception.AbstractWWordException;
import cnsukidayo.com.gitee.model.support.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controller层的异常处理器
 *
 * @author cnsukidayo
 */
@RestControllerAdvice(value = {"cnsukidayo.com.gitee.controller.content",
        "cnsukidayo.com.gitee.controller.authentication"})
public class ControllerExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义的所有异常
     *
     * @param e 捕获到的异常
     * @return 返回响应信息
     */
    @ExceptionHandler(AbstractWWordException.class)
    public ResponseEntity<BaseResponse<?>> handleWWordException(AbstractWWordException e) {
        BaseResponse<Object> baseResponse = handleBaseException(e);
        baseResponse.setStatus(e.getStatus().value());
        baseResponse.setData(e.getErrorData());
        return new ResponseEntity<>(baseResponse, e.getStatus());
    }

    /**
     * 处理参数校验失败的异常
     *
     * @param e 捕获到的参数校验失败异常
     * @return 返回响应信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        BaseResponse<Map<String, String>> baseResponse = handleBaseException(e);
        baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        baseResponse.setMessage("字段验证错误，请完善后重试！");
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, String> errMap = new HashMap<>(4);
        fieldErrors.forEach(
                filedError -> errMap.put(filedError.getField(), filedError.getDefaultMessage()));
        baseResponse.setData(errMap);
        return baseResponse;
    }

    /**
     * 处理全局的异常,拦截器捕获异常的最后兜底的方法
     *
     * @param e 捕获到的异常
     * @return 返回响应信息
     */
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

