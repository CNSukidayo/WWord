package cnsukidayo.com.gitee.security.filter;

import cnsukidayo.com.gitee.exception.AuthenticationException;
import cnsukidayo.com.gitee.model.pojo.User;
import cnsukidayo.com.gitee.security.authentication.AuthenticationImpl;
import cnsukidayo.com.gitee.security.context.SecurityContextHolder;
import cnsukidayo.com.gitee.security.context.SecurityContextImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Api authentication Filter
 *
 * @author johnniang
 */
@Component
@Order(0)
public class ApiAuthenticationFilter extends AbstractAuthenticationFilter {

    public ApiAuthenticationFilter() {
        addUrlPatterns("/api/user/**");
        addExcludeUrlPatterns("/api/user/login");
    }

    @Override
    protected void doAuthenticate(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 获取用户请求的token
        String token = getTokenFromRequest(request);

        if (!StringUtils.hasText(token)) {
            throw new AuthenticationException("未登录，请登录后访问");
        }

        User user = new User();

        // 设置安全性
        SecurityContextHolder
                .setContext(new SecurityContextImpl(new AuthenticationImpl(user)));

        // 执行过滤链
        filterChain.doFilter(request, response);
    }


}
