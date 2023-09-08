package io.github.cnsukidayo.wword.model.environment;

import org.springframework.http.HttpHeaders;

/**
 * @author sukidayo
 * @date 2023/5/17 17:06
 */
public class WWordConst {

    // 用户携带token的请求头
    public static final String API_ACCESS_KEY_HEADER_NAME = "API-" + HttpHeaders.AUTHORIZATION;

    public static final String X_CLIENT_USER = "X-CLIENT-USER";

    public static final int ACCESS_TOKEN_EXPIRED_SECONDS = 24 * 3600;

    public static final int REFRESH_TOKEN_EXPIRED_DAYS = 30;

    // 用户昵称前缀
    public static final String USER_NICK_PREFIX = "用户_";

}
