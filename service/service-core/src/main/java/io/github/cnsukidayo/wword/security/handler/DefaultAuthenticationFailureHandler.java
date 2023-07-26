package io.github.cnsukidayo.wword.security.handler;

import io.github.cnsukidayo.wword.exception.AbstractWWordException;
import io.github.cnsukidayo.wword.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import java.io.IOException;

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

        BaseResponse<Object> errorDetail = new BaseResponse<>();

        errorDetail.setStatus(exception.getStatus().value());
        errorDetail.setMessage(exception.getMessage());
        errorDetail.setData(exception.getErrorData());

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(exception.getStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(errorDetail));
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
