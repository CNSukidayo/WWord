package io.github.cnsukidayo.wword.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.cnsukidayo.wword.auth.client.UserFeignClient;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    private final RedisTemplate<String, String> redisTemplate;

    private final UserFeignClient userFeignClient;

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    public GlobalAuthenticationFilter(RedisTemplate<String, String> redisTemplate,
                                      @Lazy UserFeignClient userFeignClient) {
        this.redisTemplate = redisTemplate;
        this.userFeignClient = userFeignClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(WWordConst.API_ACCESS_KEY_HEADER_NAME);
        // 调用鉴权模块进行鉴权,看目标用户是否有当前接口的权限,如果有则放行,否则显示错误信息
        CheckAuthParam checkAuthParam = new CheckAuthParam();
        checkAuthParam.setToken(token);
        checkAuthParam.setTargetUrl(exchange.getRequest().getURI().getPath());
        Future<BaseResponse<Object>> future = executorService.submit(() -> userFeignClient.getAndCheck(checkAuthParam));
        try {
            BaseResponse<Object> result = future.get();
            if (!result.getStatus().equals(HttpStatus.OK.value())) {
                // 处理异常,将子服务的异常响应给用户
                return getFailureHandler().onFailure(exchange.getRequest(), exchange.getResponse(), result);
            } else {
                // 封装请求的用户信息
                ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
                builder.header(WWordConst.X_CLIENT_USER, JsonUtils.objectToJson(result.getData()));
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
