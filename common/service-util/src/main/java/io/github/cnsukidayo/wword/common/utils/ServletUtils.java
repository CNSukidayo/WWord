package io.github.cnsukidayo.wword.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * @author sukidayo
 * @date 2023/7/30 20:27
 */
public class ServletUtils {

    private ServletUtils() {
    }

    /**
     * 得到当前的http servlet request
     *
     * @return an optional http servlet request
     */
    public static Optional<HttpServletRequest> getCurrentRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> requestAttributes instanceof ServletRequestAttributes)
                .map(requestAttributes -> (ServletRequestAttributes) requestAttributes)
                .map(ServletRequestAttributes::getRequest);
    }

    /**
     * 得到请求的IP地址
     *
     * @return 返回ip地址或者null
     */
    public static String getRequestIp() {
        return getCurrentRequest().map(ServletUtils::getClientIP).orElse(null);
    }

    /**
     * 获取客户端IP.
     *
     * <p>
     * 默认检测的Header:
     *
     * <pre>
     * 1、X-Forwarded-For
     * 2、X-Real-IP
     * 3、Proxy-Client-IP
     * 4、WL-Proxy-Client-IP
     * </pre>
     *
     * <p>
     * otherHeaderNames参数用于自定义检测的Header<br>
     * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
     * </p>
     *
     * @param request          请求对象{@link HttpServletRequest}
     * @param otherHeaderNames 其他自定义头文件，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     * @see <a href="https://github.com/dromara/hutool/blob/v5-master/hutool-extra/src/main/java/cn/hutool/extra/servlet/ServletUtil.java">查看来源</a>
     */
    public static String getClientIP(HttpServletRequest request, String... otherHeaderNames) {
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        if (ArrayUtils.isNotEmpty(otherHeaderNames)) {
            headers = ArrayUtils.addAll(headers, otherHeaderNames);
        }

        return getClientIPByHeader(request, headers);
    }


    /**
     * 检测给定字符串是否为未知，多用于检测HTTP请求相关.
     *
     * @param checkString 被检测的字符串
     * @return 是否未知
     * @since 1.4.13
     */
    public static boolean unknown(String checkString) {
        return StringUtils.hasLength(checkString) && !"unknown".equalsIgnoreCase(checkString);
    }

    /**
     * 获取客户端IP.
     *
     * <p>
     * headerNames参数用于自定义检测的Header<br>
     * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
     * </p>
     *
     * @param request     请求对象{@link HttpServletRequest}
     * @param headerNames 自定义头，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     * @see <a href="https://github.com/dromara/hutool/blob/v5-master/hutool-extra/src/main/java/cn/hutool/extra/servlet/ServletUtil.java">查看来源</a>
     * @since 1.4.13
     */
    public static String getClientIPByHeader(HttpServletRequest request, String... headerNames) {
        String ip;
        for (String header : headerNames) {
            ip = request.getHeader(header);
            if (unknown(ip)) {
                return getMultistageReverseProxyIp(ip);
            }
        }

        ip = request.getRemoteAddr();
        return getMultistageReverseProxyIp(ip);
    }


    /**
     * 从多级反向代理中获得第一个非unknown IP地址.
     *
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     * @since 1.4.13
     */
    public static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                if (unknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }
}
