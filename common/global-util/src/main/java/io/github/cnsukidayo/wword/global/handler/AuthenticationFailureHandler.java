package io.github.cnsukidayo.wword.global.handler;

import io.github.cnsukidayo.wword.global.exception.AbstractWWordException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * Authentication failure handler.
 *
 * @author cnsukidayo
 */
public interface AuthenticationFailureHandler {

    /**
     * Calls when a user has been unsuccessfully authenticated.
     *
     * @param request   http servlet request
     * @param response  http servlet response
     * @param exception api exception
     * @throws IOException      io exception
     * @throws ServletException service exception
     */
    void onFailure(HttpServletRequest request, HttpServletResponse response,
                   AbstractWWordException exception) throws IOException, ServletException;

    Mono<Void> onFailure(ServerHttpRequest request, ServerHttpResponse response,
                   Object errorDetail);


}
