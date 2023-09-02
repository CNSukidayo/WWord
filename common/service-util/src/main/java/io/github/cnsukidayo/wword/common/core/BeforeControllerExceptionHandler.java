package io.github.cnsukidayo.wword.common.core;

import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import jakarta.servlet.ServletException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * controller层的异常处理器,仅仅是应用内的方法出现异常时会被次控制器捕获.<br>
 * 所有的异常被捕获到了最终返回的状态码都是200<br>
 * 由前端根据{@link ErrorVo}中封装的code参数来判断本次请求成功与否的情况<br>
 * 后端传给前端的错误提示信息(即为什么当前产生了该异常)应该放到ErrorVo的message字段中.
 *
 * @author cnsukidayo
 */
@RestControllerAdvice
public class BeforeControllerExceptionHandler extends BaseExceptionHandler {

    public BeforeControllerExceptionHandler(@Value("${knife4j.production}") Boolean production) {
        super(production, LoggerFactory.getLogger(BeforeControllerExceptionHandler.class));
    }

    /**
     * 进入controller层前的相关异常
     * {@link NoHandlerFoundException} 根据请求URL进行路径匹配有没有对应的控制器,如果没有该控制器则会抛出该异常<br>
     * {@link HttpRequestMethodNotSupportedException} 如果能够匹配到目标路径,但是匹配到的控制的请求方法不对,
     * GET或者POST不匹配会抛出该异常<br>
     * {@link HttpMediaTypeNotSupportedException} 请求的媒体类型不匹配时会产生该异常(主要是content-type参数不对)
     * 例如post方法需要一个json请求体;但是请求的content-type值没有包含application/json会抛出该异常.<br>
     * 再例如SSE协议的问题,也是需要请求头支持的
     * {@link MissingPathVariableException} 未检测到路径参数,说白了就是请求路径形式类似:/save/{userId} 并且在controller
     * 方法的形参中有注解标注了@PathVariable("userId"),但是用户请求的路径仅仅只是/save 并没有包含后面的路径参数时会产生该异常.<br>
     * {@link MissingServletRequestParameterException} 缺少请求参数,和MissingPathVariableException异常类似;该异常是@RequestParam
     * 注解需要的参数没有传递时产生的异常.<br>
     * {@link TypeMismatchException} 参数类型匹配失败.例如:接收参数为Long型,但传入的是一个字符串类型时会抛出该异常.<br>
     * {@link HttpMessageNotReadableException} 与上面的HttpMediaTypeNotSupportedException举的例子完全相反
     * 即请求头携带了"content-type: application/json;charset=UTF-8",但接收参数却没有添加注解@RequestBody;
     * 或者请求体携带的 json 串反序列化成 pojo 的过程中失败了,也会抛出该异常.<br>
     * {@link HttpMessageNotWritableException} 返回的pojo在序列化成json的过程中失败了会抛出该异常.
     *
     * @param exception 捕获到的异常
     * @return 最终返回给前端的响应信息
     */
    @ExceptionHandler({
        NoHandlerFoundException.class,
        HttpRequestMethodNotSupportedException.class,
        HttpMediaTypeNotSupportedException.class,
        MissingPathVariableException.class,
        MissingServletRequestParameterException.class,
        TypeMismatchException.class,
        HttpMessageNotReadableException.class,
        HttpMessageNotWritableException.class,
        // BindException.class,
        // MethodArgumentNotValidException.class
        HttpMediaTypeNotAcceptableException.class,
        ServletRequestBindingException.class,
        ConversionNotSupportedException.class,
        MissingServletRequestPartException.class,
        AsyncRequestTimeoutException.class
    })
    public ResponseEntity<ErrorVo> handleServletException(Exception exception) {
        if (exception instanceof ServletException servletException) {
            return handleBaseServletException(servletException);
        }
        BaseResponse<ErrorVo> errorVoBaseResponse = handleBaseException(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorVoBaseResponse.getData());
    }


}

