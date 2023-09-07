package io.github.cnsukidayo.wword.gateway.utils;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * 安全工具
 *
 * @author cnsukidayo
 * @date 2023/5/19 18:11
 */
public class SecurityUtils {

    /**
     * 验证令牌的前缀
     */
    private static final String TOKEN_ACCESS_CACHE_PREFIX = "user.access.token.";

    /**
     * 刷新令牌的前缀
     */
    private static final String TOKEN_REFRESH_CACHE_PREFIX = "user.refresh.token.";

    private SecurityUtils() {
    }

    @NonNull
    public static String buildTokenAccessKey(@NonNull String accessToken) {
        Assert.hasText(accessToken, "Access token must not be blank");
        // 从access_token->user
        return TOKEN_ACCESS_CACHE_PREFIX + accessToken;
    }

    @NonNull
    public static String buildTokenRefreshKey(@NonNull String refreshToken) {
        Assert.hasText(refreshToken, "Refresh token must not be blank");
        // 从refresh_token->user
        return TOKEN_REFRESH_CACHE_PREFIX + refreshToken;
    }

}
