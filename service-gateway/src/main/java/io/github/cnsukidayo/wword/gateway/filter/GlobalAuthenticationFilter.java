package io.github.cnsukidayo.wword.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.cnsukidayo.wword.auth.client.UserFeignClient;
import io.github.cnsukidayo.wword.gateway.config.properties.WWordProperties;
import io.github.cnsukidayo.wword.global.handler.AuthenticationFailureHandler;
import io.github.cnsukidayo.wword.global.handler.DefaultAuthenticationFailureHandler;
import io.github.cnsukidayo.wword.global.utils.JsonUtils;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import io.github.cnsukidayo.wword.model.params.CheckAuthParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author sukidayo
 * @date 2023/9/7 19:05
 */
@Component
@Order(0)
public class GlobalAuthenticationFilter implements GlobalFilter {

    private volatile AuthenticationFailureHandler failureHandler;

    private final UserFeignClient userFeignClient;

    // todo 这一步会影响性能
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    private final WWordProperties wWordProperties;

    private final AntPathMatcher antPathMatcher;

    public GlobalAuthenticationFilter(@Lazy UserFeignClient userFeignClient,
                                      WWordProperties wWordProperties) {
        this.userFeignClient = userFeignClient;
        this.wWordProperties = wWordProperties;
        this.antPathMatcher = new AntPathMatcher();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String targetUrl = exchange.getRequest().getURI().getPath();
        // 检查白名单
        if (wWordProperties.getExcludeUrlPatterns().stream().anyMatch(p -> antPathMatcher.match(p, targetUrl))) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(WWordConst.API_ACCESS_KEY_HEADER_NAME);
        token = "7b4e2ebb0a4d4bf591ec394c7fff5311";
        // 调用鉴权模块进行鉴权,看目标用户是否有当前接口的权限,如果有则放行,否则显示错误信息
        CheckAuthParam checkAuthParam = new CheckAuthParam();
        checkAuthParam.setToken(token);

        checkAuthParam.setTargetUrl(targetUrl);
        Future<BaseResponse<Object>> future = executorService.submit(() -> userFeignClient.getAndCheck(checkAuthParam));
        try {
            BaseResponse<Object> result = future.get();
            if (!result.getStatus().equals(HttpStatus.OK.value())) {
                // 处理异常,将子服务的异常响应给用户
                return getFailureHandler().onFailure(exchange.getRequest(), exchange.getResponse(), result);
            } else {
                // 封装请求的用户信息并进行编码,从而支持中文
                ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
                String clientUser = URLEncoder.encode(JsonUtils.objectToJson(((Map) result.getData()).get("user")), StandardCharsets.UTF_8);
                builder.header(WWordConst.X_CLIENT_USER, clientUser);
                return chain.filter(exchange.mutate().request(builder.build()).build());
            }
        } catch (InterruptedException
                 | ExecutionException
                 | JsonProcessingException e) {
            e.printStackTrace();
            // 构造一个异常并返回
            BaseResponse<ErrorVo> errorDetail = new BaseResponse<>();
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorDetail.setStatus(status.value());
            errorDetail.setMessage(status.getReasonPhrase());
            ErrorVo errorVo = new ErrorVo();
            errorVo.setTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
            errorVo.setStatus(status.value());
            errorVo.setMessage("获取用户失败!");
            errorDetail.setData(errorVo);
            return getFailureHandler().onFailure(exchange.getRequest(), exchange.getResponse(), errorDetail);
        }
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

}
