package io.github.cnsukidayo.wword.global.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cnsukidayo.wword.global.exception.AbstractWWordException;
import io.github.cnsukidayo.wword.global.utils.JsonUtils;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Default AuthenticationFailureHandler.
 *
 * @author cnsukidayo
 * @date 12/12/18
 */
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = JsonUtils.DEFAULT_JSON_MAPPER;

    public DefaultAuthenticationFailureHandler() {
    }

    @Override
    public void onFailure(HttpServletRequest request, HttpServletResponse response,
                          AbstractWWordException exception) throws IOException {

        BaseResponse<ErrorVo> errorDetail = new BaseResponse<>();

        HttpStatus status = HttpStatus.BAD_REQUEST;
        errorDetail.setStatus(status.value());
        errorDetail.setMessage(status.getReasonPhrase());
        ErrorVo errorVo = new ErrorVo();
        errorVo.setTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        errorVo.setStatus(exception.getStatus());
        errorVo.setMessage(exception.getMessage());
        errorDetail.setData(errorVo);

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorDetail));
    }

    @Override
    public Mono<Void> onFailure(ServerHttpRequest request, ServerHttpResponse response,
                                Object errorDetail) {
        byte[] data;
        try {
            data = objectMapper.writeValueAsBytes(errorDetail);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer buffer = response.bufferFactory().wrap(data);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        // 设置响应为OK,统一起来
        response.setStatusCode(HttpStatus.OK);
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * Sets custom object mapper.
     *
     * @param objectMapper object mapper
     */
    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "Object mapper must not be null");

        this.objectMapper = objectMapper;
    }
}
