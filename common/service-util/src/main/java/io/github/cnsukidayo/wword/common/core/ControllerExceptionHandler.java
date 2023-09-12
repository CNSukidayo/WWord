package io.github.cnsukidayo.wword.common.core;

import io.github.cnsukidayo.wword.global.exception.AbstractWWordException;
import io.github.cnsukidayo.wword.global.handler.BaseExceptionHandler;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * controller层的异常处理器,仅仅是应用内的方法出现异常时会被次控制器捕获.<br>
 * 所有的异常被捕获到了最终返回的状态码都是200<br>
 * 由前端根据{@link ErrorVo}中封装的code参数来判断本次请求成功与否的情况<br>
 * 后端传给前端的错误提示信息(即为什么当前产生了该异常)应该放到ErrorVo的message字段中.
 *
 * @author cnsukidayo
 */
@RestControllerAdvice(value = {"io.github.cnsukidayo.wword.core.controller",
    "io.github.cnsukidayo.wword.admin.controller",
    "io.github.cnsukidayo.wword.auth.controller",
    "io.github.cnsukidayo.wword.search.api",
    "io.github.cnsukidayo.wword.third.oss.api"})
public class ControllerExceptionHandler extends BaseExceptionHandler {

    public ControllerExceptionHandler(@Value("${knife4j.production}") Boolean production) {
        super(production, LoggerFactory.getLogger(ControllerExceptionHandler.class));
    }


    /**
     * 处理参数校验失败的异常
     *
     * @param exception 捕获到的参数校验失败异常
     * @return 返回响应信息
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ErrorVo> handleMethodArgumentNotValidException(BindException exception) {
        // MethodArgumentNotValidException继承自BindException
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        BaseResponse<ErrorVo> baseResponse = handleBaseException(exception);
        baseResponse.getData().setStatus(baseResponse.getStatus());
        baseResponse.getData().setMessage(fieldErrors.get(0).getDefaultMessage());
        if (!getProduction()) {
            StringBuilder error = new StringBuilder();
            for (FieldError fieldError : fieldErrors) {
                error.append(fieldError.getField())
                    .append(":")
                    .append(fieldError.getDefaultMessage())
                    .append('\n');
            }
            baseResponse.getData().setError(error.toString());
        }
        return baseResponse;
    }

    /**
     * 处理自定义的所有异常
     *
     * @param exception 捕获到的异常
     * @return 返回响应信息
     */
    @ExceptionHandler(AbstractWWordException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ErrorVo> handleWWordException(AbstractWWordException exception) {
        BaseResponse<ErrorVo> baseResponse = handleBaseException(exception);
        // 设置ErrorVo的信息data
        ErrorVo data = baseResponse.getData();
        data.setStatus(exception.getStatus());
        data.setMessage(exception.getMessage());
        return baseResponse;
    }


    /**
     * 处理全局的异常,拦截器捕获异常的最后兜底的方法,此时一定是服务器异常了
     *
     * @param exception 捕获到的异常
     * @return 返回响应信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ErrorVo> handleGlobalException(Exception exception) {
        BaseResponse<ErrorVo> baseResponse = handleBaseException(exception);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        baseResponse.setStatus(status.value());
        baseResponse.setMessage(status.getReasonPhrase());
        baseResponse.getData().setStatus(status.value());
        baseResponse.getData().setMessage(status.getReasonPhrase());
        return baseResponse;
    }


}

