package io.github.cnsukidayo.wword.controller.error;

import io.github.cnsukidayo.wword.support.BaseResponse;
import io.github.cnsukidayo.wword.vo.ErrorVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 默认的错误控制器
 *
 * @author sukidayo
 * @date 2023/7/23 13:28
 */
@RestController
public class DefaultErrorController extends BasicErrorController {

    public DefaultErrorController(
            ErrorAttributes errorAttributes,
            ServerProperties serverProperties,
            List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @GetMapping
    public BaseResponse<ErrorVo> handleError(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return BaseResponse.build(status.value(), status.getReasonPhrase(), null);
        }
        ErrorVo errorVo = new ErrorVo();
        Map<String, Object> errorAttributes = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
        errorVo.setTimestamp(((Date) errorAttributes.get("timestamp")).getTime());
        errorVo.setStatus((Integer) errorAttributes.get("status"));
        errorVo.setError((String) errorAttributes.get("error"));
        errorVo.setPath((String) errorAttributes.get("path"));
        return BaseResponse.build(status.value(), status.getReasonPhrase(), errorVo);
    }

}
