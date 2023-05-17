package cnsukidayo.com.gitee.security.filter;

import cnsukidayo.com.gitee.exception.AbstractWWordException;
import cnsukidayo.com.gitee.model.support.WWordConst;
import cnsukidayo.com.gitee.security.context.SecurityContextHolder;
import cnsukidayo.com.gitee.security.handler.AuthenticationFailureHandler;
import cnsukidayo.com.gitee.security.handler.DefaultAuthenticationFailureHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
 * Abstract authentication filter.
 *
 * @author johnniang
 * @date 19-4-16
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

    AbstractAuthenticationFilter() {
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
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(WWordConst.USER_TOKEN)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    protected abstract void doAuthenticate(HttpServletRequest request, HttpServletResponse response,
                                           FilterChain filterChain) throws ServletException, IOException;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        Assert.notNull(request, "Http servlet request must not be null");

        // check white list
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
     * Sets authentication failure handler.
     *
     * @param failureHandler authentication failure handler
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
