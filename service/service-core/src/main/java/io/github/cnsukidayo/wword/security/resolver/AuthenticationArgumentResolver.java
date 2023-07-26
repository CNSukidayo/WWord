package io.github.cnsukidayo.wword.security.resolver;

import io.github.cnsukidayo.wword.exception.AuthenticationException;
import io.github.cnsukidayo.wword.pojo.User;
import io.github.cnsukidayo.wword.security.authentication.Authentication;
import io.github.cnsukidayo.wword.security.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

/**
 * 权限参数处理器
 *
 * @author sukidayo
 * @date 2023/7/26 12:06
 */
@Slf4j
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    public AuthenticationArgumentResolver() {
        log.debug("初始化AuthenticationArgumentResolver");
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return Authentication.class.isAssignableFrom(parameterType)
                || User.class.isAssignableFrom(parameterType);
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter,
                                  @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  @Nullable WebDataBinderFactory binderFactory) {
        log.debug("处理权限参数");

        Class<?> parameterType = parameter.getParameterType();

        Authentication authentication =
                Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                        .orElseThrow(() -> new AuthenticationException("你还没有登陆"));

        if (Authentication.class.isAssignableFrom(parameterType)) {
            return authentication;
        } else if (User.class.isAssignableFrom(parameterType)) {
            return authentication.user();
        }

        // 应该永远不会执行
        throw new UnsupportedOperationException("Unknown parameter type: " + parameterType);
    }

}