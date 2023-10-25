package io.github.cnsukidayo.wword.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * @author sukidayo
 * @date 2023/10/23 14:21
 */
@Component
@Order(-5)
public class UVCountFilter implements GlobalFilter {

    private final HyperLogLogOperations<String, String> hyperLogLogOperations;

    public UVCountFilter(RedisTemplate<String, String> redisTemplate) {
        this.hyperLogLogOperations = redisTemplate.opsForHyperLogLog();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获得请求的IP地址
        String ip = Optional.ofNullable(exchange.getRequest().getRemoteAddress())
            .orElse(new InetSocketAddress("127.0.0.1", 443))
            .getAddress()
            .getHostAddress();
        // 统计UV
        hyperLogLogOperations.add("today", ip);
        return chain.filter(exchange);
    }
}
