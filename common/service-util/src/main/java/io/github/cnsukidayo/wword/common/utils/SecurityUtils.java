package io.github.cnsukidayo.wword.common.utils;

import io.github.cnsukidayo.wword.model.entity.User;
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
    private static final String TOKEN_ACCESS_CACHE_PREFIX = "wword:auth:user:token:access.token.";

    /**
     * 刷新令牌的前缀
     */
    private static final String TOKEN_REFRESH_CACHE_PREFIX = "wword:auth:user:token:refresh.token.";

    private static final String ACCESS_TOKEN_CACHE_PREFIX = "wword:auth:user:token:access_token.";

    private static final String REFRESH_TOKEN_CACHE_PREFIX = "wword:auth:user:token:refresh_token.";

    private SecurityUtils() {
    }

    @NonNull
    public static String buildAccessTokenKey(@NonNull User user) {
        Assert.notNull(user, "User must not be null");
        // 从User->access_token
        return ACCESS_TOKEN_CACHE_PREFIX + user.getUuid();
    }

    @NonNull
    public static String buildRefreshTokenKey(@NonNull User user) {
        Assert.notNull(user, "User must not be null");
        // 从User->refresh_token
        return REFRESH_TOKEN_CACHE_PREFIX + user.getUuid();
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
