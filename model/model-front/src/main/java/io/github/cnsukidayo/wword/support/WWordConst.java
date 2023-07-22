package io.github.cnsukidayo.wword.support;

import org.springframework.http.HttpHeaders;

/**
 * @author sukidayo
 * @date 2023/5/17 17:06
 */
public class WWordConst {

    // 用户携带token的请求头
    public static final String API_ACCESS_KEY_HEADER_NAME = "API-" + HttpHeaders.AUTHORIZATION;

    public static final int ACCESS_TOKEN_EXPIRED_SECONDS = 24 * 3600;

    public static final int REFRESH_TOKEN_EXPIRED_DAYS = 30;

}
