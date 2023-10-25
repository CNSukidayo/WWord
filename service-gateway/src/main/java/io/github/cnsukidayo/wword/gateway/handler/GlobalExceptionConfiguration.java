package io.github.cnsukidayo.wword.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cnsukidayo.wword.global.handler.BaseExceptionHandler;
import io.github.cnsukidayo.wword.global.utils.JsonUtils;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author lengleng
 * @date 2020/5/23
 * <p>
 * 网关异常通用处理器，只作用在webflux 环境下 , 优先级低于 {@link ResponseStatusExceptionHandler} 执行
 */
@Order(-1)
@Component
public class GlobalExceptionConfiguration extends BaseExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper = JsonUtils.DEFAULT_JSON_MAPPER;


    public GlobalExceptionConfiguration(@Value("${wword.production}") Boolean production) {
        super(production, LoggerFactory.getLogger(GlobalExceptionConfiguration.class));
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // header set_json响应
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        BaseResponse<ErrorVo> errorVo = super.handleBaseException(ex);
        errorVo.getData().setStatus(errorVo.getStatus());
        return response
            .writeWith(Mono.fromSupplier(() -> {
                DataBufferFactory bufferFactory = response.bufferFactory();
                try {
                    //返回json异常原因给前端
                    return bufferFactory.wrap(objectMapper.writeValueAsBytes(errorVo));
                } catch (JsonProcessingException e) {
                    getLog().warn("Error writing response", ex);
                    return bufferFactory.wrap(new byte[0]);
                }
            }));
    }
}
