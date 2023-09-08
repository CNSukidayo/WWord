package io.github.cnsukidayo.wword.common.security.filter;

import io.github.cnsukidayo.wword.global.exception.AbstractWWordException;
import io.github.cnsukidayo.wword.common.security.context.SecurityContextHolder;
import io.github.cnsukidayo.wword.global.handler.AuthenticationFailureHandler;
import io.github.cnsukidayo.wword.global.handler.DefaultAuthenticationFailureHandler;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;
import java.util.*;

/**
 * 抽象的授权过滤器
 *
 * @author cnsukidayo
 * @date 2023/5/19 18:11
 */
public abstract class AbstractAuthenticationFilter extends OncePerRequestFilter {

    protected final AntPathMatcher antPathMatcher;
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    private volatile AuthenticationFailureHandler failureHandler;
    /**
     * 排除URL模式
     */
    private Set<String> excludeUrlPatterns = new HashSet<>(16);

    private Set<String> urlPatterns = new LinkedHashSet<>();

    protected AbstractAuthenticationFilter() {
        antPathMatcher = new AntPathMatcher();
    }

    /**
     * 根据用户请求信息得到用户的token,Assert用户程序内部验证,生产的错误不传回前端.
     *
     * @param request 用户请求的request
     * @return 返回用户的token
     */
    protected String getTokenFromRequest(@NonNull HttpServletRequest request) {
        Assert.notNull(request, "Http servlet request must not be null");

        // Get from header
        return request.getHeader(WWordConst.API_ACCESS_KEY_HEADER_NAME);
    }

    protected abstract void doAuthenticate(HttpServletRequest request, HttpServletResponse response,
                                           FilterChain filterChain) throws ServletException, IOException;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        Assert.notNull(request, "Http servlet request must not be null");

        // 检查白名单
        boolean result = excludeUrlPatterns.stream()
                .anyMatch(p -> antPathMatcher.match(p, urlPathHelper.getRequestUri(request)));

        return result || urlPatterns.stream()
                .noneMatch(p -> antPathMatcher.match(p, urlPathHelper.getRequestUri(request)));
    }

    /**
     * 添加排除的URL
     *
     * @param excludeUrlPatterns exclude urls
     */
    public void addExcludeUrlPatterns(@NonNull String... excludeUrlPatterns) {
        Assert.notNull(excludeUrlPatterns, "Exclude url patterns must not be null");

        Collections.addAll(this.excludeUrlPatterns, excludeUrlPatterns);
    }

    /**
     * 得到排除的URL
     *
     * @return exclude url patterns.
     */
    @NonNull
    public Set<String> getExcludeUrlPatterns() {
        return excludeUrlPatterns;
    }

    /**
     * 设置排除的URL
     *
     * @param excludeUrlPatterns exclude urls
     */
    public void setExcludeUrlPatterns(@NonNull Collection<String> excludeUrlPatterns) {
        Assert.notNull(excludeUrlPatterns, "Exclude url patterns must not be null");

        this.excludeUrlPatterns = new HashSet<>(excludeUrlPatterns);
    }

    public Collection<String> getUrlPatterns() {
        return this.urlPatterns;
    }

    public void setUrlPatterns(Collection<String> urlPatterns) {
        Assert.notNull(urlPatterns, "UrlPatterns must not be null");
        this.urlPatterns = new LinkedHashSet<>(urlPatterns);
    }

    public void addUrlPatterns(String... urlPatterns) {
        Assert.notNull(urlPatterns, "UrlPatterns must not be null");
        Collections.addAll(this.urlPatterns, urlPatterns);
    }

    /**
     * 得到默认的错误处理器
     *
     * @return authentication failure handler
     */
    @NonNull
    private AuthenticationFailureHandler getFailureHandler() {
        if (failureHandler == null) {
            synchronized (this) {
                if (failureHandler == null) {
                    // 创建默认的身份认证失败处理程序
                    this.failureHandler = new DefaultAuthenticationFailureHandler();
                }
            }
        }
        return failureHandler;
    }

    /**
     * 设置认证错误处理器
     *
     * @param failureHandler 错误认证处理器
     */
    public synchronized void setFailureHandler(
            @NonNull AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "Authentication failure handler must not be null");

        this.failureHandler = failureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // Do authenticate
            doAuthenticate(request, response, filterChain);
        } catch (AbstractWWordException e) {
            getFailureHandler().onFailure(request, response, e);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

}
