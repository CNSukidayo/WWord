package io.github.cnsukidayo.wword.common.security.resolver;

import io.github.cnsukidayo.wword.common.security.authentication.Authentication;
import io.github.cnsukidayo.wword.common.security.context.SecurityContextHolder;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final Log logger = LogFactory.getLog(getClass());


    public AuthenticationArgumentResolver() {
        logger.debug("初始化AuthenticationArgumentResolver");
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
        logger.debug("处理权限参数");

        Class<?> parameterType = parameter.getParameterType();

        Authentication authentication =
                Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                        .orElseThrow(() -> new BadRequestException(ResultCodeEnum.LOGIN_FAIL));

        if (Authentication.class.isAssignableFrom(parameterType)) {
            return authentication;
        } else if (User.class.isAssignableFrom(parameterType)) {
            return authentication.user();
        }

        // 应该永远不会执行
        throw new UnsupportedOperationException("Unknown parameter type: " + parameterType);
    }

}