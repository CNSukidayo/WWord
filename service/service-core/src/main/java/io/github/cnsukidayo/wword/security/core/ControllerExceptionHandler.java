package io.github.cnsukidayo.wword.security.core;

import io.github.cnsukidayo.wword.exception.AbstractWWordException;
import io.github.cnsukidayo.wword.support.BaseResponse;
import io.github.cnsukidayo.wword.vo.ErrorVo;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * controller层的异常处理器,仅仅是应用内的方法出现异常时会被次控制器捕获.
 *
 * @author cnsukidayo
 */
@RestControllerAdvice(value = {"io.github.cnsukidayo.wword.controller.content",
        "io.github.cnsukidayo.wword.controller.u"})
public class ControllerExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义的所有异常
     *
     * @param e 捕获到的异常
     * @return 返回响应信息
     */
    @ExceptionHandler(AbstractWWordException.class)
    public ResponseEntity<BaseResponse<ErrorVo>> handleWWordException(AbstractWWordException e) {
        BaseResponse<ErrorVo> baseResponse = handleBaseException(e);
        baseResponse.setStatus(e.getStatus().value());
        baseResponse.getData().setStatus(e.getStatus().value());
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
    public BaseResponse<ErrorVo> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        BaseResponse<ErrorVo> baseResponse = handleBaseException(e);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        baseResponse.setStatus(status.value());
        baseResponse.setMessage("字段验证错误,请完善后重试!");
        baseResponse.getData().setStatus(status.value());
        baseResponse.getData().setMessage(fieldErrors.get(0).getDefaultMessage());
        baseResponse.getData().setPath(e.getBindingResult().getNestedPath());
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
    public BaseResponse<ErrorVo> handleGlobalException(Exception e) {
        BaseResponse<ErrorVo> baseResponse = handleBaseException(e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        baseResponse.setStatus(status.value());
        baseResponse.setMessage(status.getReasonPhrase());
        baseResponse.getData().setStatus(status.value());
        baseResponse.getData().setMessage(status.getReasonPhrase());
        return baseResponse;
    }

    private BaseResponse<ErrorVo> handleBaseException(Throwable t) {
        Assert.notNull(t, "Throwable must not be null");

        BaseResponse<ErrorVo> baseResponse = new BaseResponse<>();
        baseResponse.setMessage(t.getMessage());
        // 设置时间戳和错误信息
        ErrorVo errorVo = new ErrorVo();
        errorVo.setTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        errorVo.setMessage(t.getMessage());
        baseResponse.setData(errorVo);

        log.error("Captured an exception:", t);
        return baseResponse;
    }


}

