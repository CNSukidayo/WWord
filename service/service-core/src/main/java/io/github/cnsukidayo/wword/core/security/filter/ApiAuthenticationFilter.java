package io.github.cnsukidayo.wword.core.security.filter;

import io.github.cnsukidayo.wword.common.security.authentication.AuthenticationImpl;
import io.github.cnsukidayo.wword.common.security.context.SecurityContextHolder;
import io.github.cnsukidayo.wword.common.security.context.SecurityContextImpl;
import io.github.cnsukidayo.wword.common.security.filter.AbstractAuthenticationFilter;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.global.utils.JsonUtils;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 用户端授权过滤器
 *
 * @author cnsukidayo
 * @date 2023/5/19 18:11
 */
@Component
@Order(0)
public class ApiAuthenticationFilter extends AbstractAuthenticationFilter {

    public ApiAuthenticationFilter() {
        addUrlPatterns("/api/u/**");
        addExcludeUrlPatterns(
            "/api/u/user/login",
            "/api/u/user/register",
            "/api/u/user/refresh/*");
    }

    @Override
    protected void doAuthenticate(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 获取用户请求的token
        String userJson = request.getHeader(WWordConst.X_CLIENT_USER);

        if (!StringUtils.hasText(userJson)) {
            throw new BadRequestException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误!");
        }

        User user = JsonUtils.jsonToObject(userJson, User.class);
        // 设置安全性
        SecurityContextHolder
            .setContext(new SecurityContextImpl(new AuthenticationImpl(user)));
        // 执行过滤链
        filterChain.doFilter(request, response);
    }


}
