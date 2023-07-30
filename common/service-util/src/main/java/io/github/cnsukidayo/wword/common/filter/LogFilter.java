package io.github.cnsukidayo.wword.common.filter;

import io.github.cnsukidayo.wword.common.utils.ServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author sukidayo
 * @date 2023/7/30 20:25
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LogFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String remoteAddr = ServletUtils.getClientIP(request);

        log.debug("Starting url: [{}], method: [{}], ip: [{}]",
                request.getRequestURL(),
                request.getMethod(),
                remoteAddr);

        // 得到开始时间
        final long startTime = System.currentTimeMillis();

        // 执行过滤
        filterChain.doFilter(request, response);

        log.debug("Ending   url: [{}], method: [{}], ip: [{}], status: [{}], usage: [{}] ms",
                request.getRequestURL(),
                request.getMethod(),
                remoteAddr,
                response.getStatus(),
                System.currentTimeMillis() - startTime);
    }
}