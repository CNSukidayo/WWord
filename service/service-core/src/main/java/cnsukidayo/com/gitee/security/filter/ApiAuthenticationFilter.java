package cnsukidayo.com.gitee.security.filter;

import cnsukidayo.com.gitee.exception.AuthenticationException;
import cnsukidayo.com.gitee.security.authentication.AuthenticationImpl;
import cnsukidayo.com.gitee.security.context.SecurityContextHolder;
import cnsukidayo.com.gitee.security.context.SecurityContextImpl;
import cnsukidayo.com.gitee.security.util.SecurityUtils;
import cnsukidayo.com.gitee.service.UserService;
import io.github.cnsukidayo.wword.pojo.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * API授权过滤器
 *
 * @author cnsukidayo
 * @date 2023/5/19 18:11
 */
@Component
@Order(0)
public class ApiAuthenticationFilter extends AbstractAuthenticationFilter {

    private final UserService userService;
    private final RedisTemplate<String, String> redisTemplate;

    public ApiAuthenticationFilter(UserService userService,
                                   RedisTemplate<String, String> redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        addUrlPatterns("/api/authentication/**");
        addExcludeUrlPatterns("/api/authentication/user/login",
                "/api/authentication/user/refresh/*");
    }

    @Override
    protected void doAuthenticate(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 获取用户请求的token
        String token = getTokenFromRequest(request);

        if (!StringUtils.hasText(token)) {
            throw new AuthenticationException("未登录，请登录后访问");
        }
        String userID = redisTemplate.opsForValue().get(SecurityUtils.buildTokenAccessKey(token));
        if (userID == null) {
            throw new AuthenticationException("token不存在或已过期").setErrorData(token);
        }
        User user = userService.getById(Integer.valueOf(userID));

        // 设置安全性
        SecurityContextHolder
                .setContext(new SecurityContextImpl(new AuthenticationImpl(user)));
        // 执行过滤链
        filterChain.doFilter(request, response);
    }


}
