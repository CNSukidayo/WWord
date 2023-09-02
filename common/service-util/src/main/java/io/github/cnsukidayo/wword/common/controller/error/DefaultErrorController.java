//package io.github.cnsukidayo.wword.common.controller.error;
//
//import io.github.cnsukidayo.wword.model.support.BaseResponse;
//import io.github.cnsukidayo.wword.model.vo.ErrorVo;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.boot.autoconfigure.web.ErrorProperties;
//import org.springframework.boot.autoconfigure.web.ServerProperties;
//import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
//import org.springframework.boot.web.error.ErrorAttributeOptions;
//import org.springframework.boot.web.servlet.error.ErrorAttributes;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//import java.util.Map;
//
///**
// * 默认的错误控制器
// *
// * @author sukidayo
// * @date 2023/7/23 13:28
// */
//@RestController
//@RequestMapping("${server.error.path:${error.path:/error}}")
//public class DefaultErrorController extends AbstractErrorController {
//
//    private final ErrorProperties errorProperties;
//
//    public DefaultErrorController(ErrorAttributes errorAttributes,
//                                  ServerProperties serverProperties) {
//        super(errorAttributes);
//        this.errorProperties = serverProperties.getError();
//    }
//
//    @RequestMapping
//    public BaseResponse<ErrorVo> handleError(HttpServletRequest request) {
//        HttpStatus status = getStatus(request);
//        if (status == HttpStatus.NO_CONTENT) {
//            return BaseResponse.build(status.value(), status.getReasonPhrase(), null);
//        }
//        ErrorVo errorVo = new ErrorVo();
//        Map<String, Object> errorAttributes = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
//        errorVo.setTimestamp(((Date) errorAttributes.get("timestamp")).getTime());
//        errorVo.setStatus((Integer) errorAttributes.get("status"));
//        errorVo.setError((String) errorAttributes.get("error"));
//        errorVo.setPath((String) errorAttributes.get("path"));
//        return BaseResponse.build(status.value(), status.getReasonPhrase(), errorVo);
//    }
//
//    protected ErrorAttributeOptions getErrorAttributeOptions(HttpServletRequest request, MediaType mediaType) {
//        ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
//        if (this.errorProperties.isIncludeException()) {
//            options = options.including(ErrorAttributeOptions.Include.EXCEPTION);
//        }
//        if (isIncludeStackTrace(request, mediaType)) {
//            options = options.including(ErrorAttributeOptions.Include.STACK_TRACE);
//        }
//        if (isIncludeMessage(request, mediaType)) {
//            options = options.including(ErrorAttributeOptions.Include.MESSAGE);
//        }
//        if (isIncludeBindingErrors(request, mediaType)) {
//            options = options.including(ErrorAttributeOptions.Include.BINDING_ERRORS);
//        }
//        return options;
//    }
//
//    protected boolean isIncludeMessage(HttpServletRequest request, MediaType produces) {
//        return switch (getErrorProperties().getIncludeMessage()) {
//            case ALWAYS -> true;
//            case ON_PARAM -> getMessageParameter(request);
//            default -> false;
//        };
//    }
//
//    protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
//        return switch (getErrorProperties().getIncludeStacktrace()) {
//            case ALWAYS -> true;
//            case ON_PARAM -> getTraceParameter(request);
//            default -> false;
//        };
//    }
//
//    protected boolean isIncludeBindingErrors(HttpServletRequest request, MediaType produces) {
//        return switch (getErrorProperties().getIncludeBindingErrors()) {
//            case ALWAYS -> true;
//            case ON_PARAM -> getErrorsParameter(request);
//            default -> false;
//        };
//    }
//
//    protected ErrorProperties getErrorProperties() {
//        return this.errorProperties;
//    }
//
//}
