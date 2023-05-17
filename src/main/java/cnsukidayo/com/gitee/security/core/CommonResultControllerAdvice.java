package cnsukidayo.com.gitee.security.core;

import cnsukidayo.com.gitee.model.support.BaseResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Controller advice for comment result.
 *
 * @author johnniang
 */
@ControllerAdvice("cnsukidayo.com.gitee.controller")
public class CommonResultControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof BaseResponse) {
            return body;
        } else if (body == null) {
            return BaseResponse.ok("success");
        } else {
            return BaseResponse.ok(body);
        }
    }
}
