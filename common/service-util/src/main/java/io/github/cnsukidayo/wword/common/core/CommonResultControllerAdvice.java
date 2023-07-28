package io.github.cnsukidayo.wword.common.core;

import io.github.cnsukidayo.wword.model.support.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一的返回值控制器,正常返回时会走该全局控制器
 *
 * @author cnsukidayo
 */
@ControllerAdvice(value = {"io.github.cnsukidayo.wword.core.controller"})
public class CommonResultControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        // 判断哪些响应需要拦截,如果是AbstractJackson的消息转换器就拦截,也就是如果返回结果需要被Json序列化的话就通过
        return AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    @NonNull
    public final Object beforeBodyWrite(@Nullable Object body,
                                        @NonNull MethodParameter returnType,
                                        @NonNull MediaType contentType,
                                        @NonNull Class<? extends HttpMessageConverter<?>> converterType,
                                        @NonNull ServerHttpRequest request,
                                        @NonNull ServerHttpResponse response) {
        // 如果body是MappingJacksonValue则直接返回,否则就将body包装到MappingJacksonValue中,在我的代码中应该都不会是MappingJacksonValue
        MappingJacksonValue container = getOrCreateContainer(body);
        beforeBodyWriteInternal(container, contentType, returnType, request, response);
        return container;
    }

    /**
     *
     */
    private MappingJacksonValue getOrCreateContainer(Object body) {
        return body instanceof MappingJacksonValue ? (MappingJacksonValue) body :
                new MappingJacksonValue(body);
    }

    private void beforeBodyWriteInternal(MappingJacksonValue bodyContainer,
                                         MediaType contentType,
                                         MethodParameter returnType,
                                         ServerHttpRequest request,
                                         ServerHttpResponse response) {
        // Get return body 获得到响应体,body.这个body就是被封装在MappingJacksonValue中的对象,也就是上面创建的对象
        Object returnBody = bodyContainer.getValue();
        /*
         有的时候在controller中会返回自定义的BaseResponse,如果这里的returnBody是BaseResponse就不使用通用的返回结果.
         有可能是别的地方BaseResponse结果,也有可能是产生异常的BaseResponse.
         */
        if (returnBody instanceof BaseResponse<?> baseResponse) {
            HttpStatus status = HttpStatus.resolve(baseResponse.getStatus());
            if (status == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            response.setStatusCode(status);
            return;
        }

        // get status
        HttpStatus status = HttpStatus.OK;
        if (response instanceof ServletServerHttpResponse) {
            HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

            status = HttpStatus.resolve(servletResponse.getStatus());
            if (status == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        BaseResponse<?> baseResponse = new BaseResponse<>(status.value(), status.getReasonPhrase(), returnBody);
        bodyContainer.setValue(baseResponse);
    }

}
