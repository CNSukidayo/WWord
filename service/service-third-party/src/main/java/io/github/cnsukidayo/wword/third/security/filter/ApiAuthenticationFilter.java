package io.github.cnsukidayo.wword.third.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cnsukidayo.wword.common.security.filter.AbstractAuthenticationFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 用户端授权过滤器
 *
 * @author cnsukidayo
 * @date 2023/5/19 18:11
 */
@Component
@Order(0)
public class ApiAuthenticationFilter extends AbstractAuthenticationFilter {

    public ApiAuthenticationFilter(ObjectMapper objectMapper) {
        super(objectMapper);
        addUrlPatterns("/api/third/party/**");
    }

}
