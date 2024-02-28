package io.github.cnsukidayo.wword.gateway.filter;

import io.github.cnsukidayo.wword.gateway.config.properties.WWordProperties;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.global.handler.AuthenticationFailureHandler;
import io.github.cnsukidayo.wword.global.handler.DefaultAuthenticationFailureHandler;
import io.github.cnsukidayo.wword.global.utils.JsonUtils;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.model.params.CheckAuthParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author sukidayo
 * @date 2023/9/7 19:05
 */
@Component
@Order(0)
public class GlobalAuthenticationFilter implements GlobalFilter {

    private volatile AuthenticationFailureHandler failureHandler;

    private final WWordProperties wWordProperties;

    private final AntPathMatcher antPathMatcher;

    private final WebClient.Builder webClientBuilder;

    public GlobalAuthenticationFilter(WWordProperties wWordProperties,
                                      WebClient.Builder webClientBuilder) {
        this.wWordProperties = wWordProperties;
        this.webClientBuilder = webClientBuilder;
        this.antPathMatcher = new AntPathMatcher();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String targetUrl = exchange.getRequest().getURI().getPath();

        // 判断是否需要执行过滤
        if (shouldNotFilter(targetUrl)) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(WWordConst.API_ACCESS_KEY_HEADER_NAME);
        // 调用鉴权模块进行鉴权,看目标用户是否有当前接口的权限,如果有则放行,否则显示错误信息
        CheckAuthParam checkAuthParam = new CheckAuthParam();
        checkAuthParam.setToken(token);
        checkAuthParam.setTargetUrl(targetUrl);
        return getPermissionList(checkAuthParam).flatMap(result -> {
            if (!result.getStatus().equals(HttpStatus.OK.value())) {
                // 处理异常,将子服务的异常响应给用户
                return getFailureHandler().onFailure(exchange.getRequest(), exchange.getResponse(), result);
            } else {
                // 封装请求的用户信息并进行编码,从而支持中文
                ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
                String clientUser = null;
                try {
                    clientUser = URLEncoder.encode(JsonUtils.objectToJson(((Map<?, ?>) result.getData()).get("user")), StandardCharsets.UTF_8);
                } catch (Exception e) {
                    return Mono.error(new BadRequestException(ResultCodeEnum.JSON_HANDLE_ERROR));
                }
                builder.header(WWordConst.X_CLIENT_USER, clientUser);
                return chain.filter(exchange.mutate().request(builder.build()).build());
            }
        });
    }

    private Mono<BaseResponse<Object>> getPermissionList(CheckAuthParam checkAuthParam) {
        return webClientBuilder.build()
            .post()
            .uri("http://service-auth/remote/auth/permission/get_and_check")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(checkAuthParam)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<>() {
            });
    }

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

    private boolean shouldNotFilter(String targetUrl) {
        Assert.hasText(targetUrl, "targetUrl must not be empty");

        boolean result = wWordProperties.getUrlPatterns().stream()
            .noneMatch(s -> antPathMatcher.match(s, targetUrl));

        return result || wWordProperties.getExcludeUrlPatterns().stream()
            .anyMatch(s -> antPathMatcher.match(s, targetUrl));
    }

}
