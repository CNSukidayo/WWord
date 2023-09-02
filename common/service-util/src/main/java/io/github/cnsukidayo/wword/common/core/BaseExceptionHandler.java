package io.github.cnsukidayo.wword.common.core;

import io.github.cnsukidayo.wword.common.utils.ExceptionUtils;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.ErrorResponse;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author sukidayo
 * @date 2023/8/31 10:03
 */
public abstract class BaseExceptionHandler {
    /**
     * 是否是生产环境
     **/
    private final Boolean production;

    private final Logger log;

    BaseExceptionHandler(Boolean production, Logger log) {
        this.production = production;
        this.log = log;
    }

    protected ResponseEntity<ErrorVo> handleBaseServletException(ServletException servletException) {
        Assert.notNull(servletException, "errorResponse must not be null");
        // 获取基本的信息
        BaseResponse<ErrorVo> baseResponse = handleBaseException(servletException);
        // 设置响应码
        ResponseEntity<ErrorVo> result = servletException instanceof ErrorResponse errorResponse ?
            ResponseEntity.status(errorResponse.getStatusCode())
                .body(baseResponse.getData())
            :
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(baseResponse.getData());
        return result;
    }


    protected BaseResponse<ErrorVo> handleBaseException(Throwable t) {
        Assert.notNull(t, "Throwable must not be null");

        BaseResponse<ErrorVo> baseResponse = new BaseResponse<>();
        // 设置时间戳和错误信息,设置外层的响应码和响应信息.具体的业务异常自已解决
        ErrorVo errorVo = new ErrorVo();
        errorVo.setTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        errorVo.setMessage("客户端错误!");
        HttpStatus status = HttpStatus.BAD_REQUEST;
        baseResponse.setStatus(status.value());
        baseResponse.setMessage(status.getReasonPhrase());
        // 如果不是生产环境则输出额外信息
        if (!getProduction()) {
            errorVo.setError(t.getMessage());
            errorVo.setTrace(ExceptionUtils.getStackTrace(t));
        }
        baseResponse.setData(errorVo);

        log.error("Captured an exception:", t);
        return baseResponse;
    }


    public Logger getLog() {
        return log;
    }

    public Boolean getProduction() {
        return production;
    }
}
