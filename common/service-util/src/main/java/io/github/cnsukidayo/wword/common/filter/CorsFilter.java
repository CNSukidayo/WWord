//package io.github.cnsukidayo.wword.common.filter;
//
//import io.github.cnsukidayo.wword.model.environment.WWordConst;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.web.cors.CorsUtils;
//import org.springframework.web.filter.GenericFilterBean;
//
//import java.io.IOException;
//
///**
// * 跨域请求过滤器
// *
// * @author sukidayo
// * @date 2023/7/30 20:17
// */
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE + 10)
//public class CorsFilter extends GenericFilterBean {
//
//    // 允许哪些请求头通过,这里允许token通过请求
//    private static final String ALLOW_HEADERS = StringUtils
//            .joinWith(",", HttpHeaders.CONTENT_TYPE, WWordConst.API_ACCESS_KEY_HEADER_NAME);
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//
//        // 设置自定义请求头
//        String originHeaderValue = httpServletRequest.getHeader(HttpHeaders.ORIGIN);
//        if (StringUtils.isNotBlank(originHeaderValue)) {
//            httpServletResponse
//                    .setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, originHeaderValue);
//        }
//        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOW_HEADERS);
//        httpServletResponse
//                .setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
//        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
//
//        if (!CorsUtils.isPreFlightRequest(httpServletRequest)) {
//            chain.doFilter(httpServletRequest, httpServletResponse);
//        }
//    }
//}
