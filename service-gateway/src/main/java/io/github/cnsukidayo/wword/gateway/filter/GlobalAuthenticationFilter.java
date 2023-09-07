package io.github.cnsukidayo.wword.gateway.filter;

import io.github.cnsukidayo.wword.auth.client.UserFeignClient;
import io.github.cnsukidayo.wword.gateway.utils.SecurityUtils;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.global.handler.AuthenticationFailureHandler;
import io.github.cnsukidayo.wword.global.handler.DefaultAuthenticationFailureHandler;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
        if (!StringUtils.hasText(token)) {
            return getFailureHandler().onFailure(exchange.getRequest(), exchange.getResponse(),
                new BadRequestException(ResultCodeEnum.LOGIN_FAIL.getCode(),
                    "token不存在或已过期"));
        }
        String userID = redisTemplate.opsForValue().get(SecurityUtils.buildTokenAccessKey(token));
        if (userID == null) {
            return getFailureHandler().onFailure(exchange.getRequest(), exchange.getResponse(),
                new BadRequestException(ResultCodeEnum.LOGIN_FAIL.getCode(),
                    "token不存在或已过期:" + token));
        }
        // 调用鉴权模块进行鉴权,看目标用户是否有当前接口的权限,如果有则放行,否则显示错误信息
        Future<User> future = executorService.submit(() -> userFeignClient.getById(userID));
        try {
            User user = future.get();
        } catch (InterruptedException | ExecutionException e) {
            return getFailureHandler().onFailure(exchange.getRequest(), exchange.getResponse(),
                new BadRequestException(ResultCodeEnum.LOGIN_FAIL.getCode(),
                    "用户获取失败"));
        }
        return chain.filter(exchange);
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
