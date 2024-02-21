package io.github.cnsukidayo.wword.global.support.constant;

/**
 * @author sukidayo
 * @date 2024/2/9 14:12
 */
public class RedisConstant {
    /**
     * 系统信息key
     */
    public static final String SYSTEM_INFO = "wword:core:user:systemInfo:%s";

    /**
     * 单词详细信息
     */
    public static final String WORD_DETAIL = "wword:core:user:wordDetail:%s";

    /**
     * 验证令牌的前缀
     */
    public static final String TOKEN_ACCESS_CACHE_PREFIX = "wword:auth:user:token:access.token.";

    /**
     * 刷新令牌的前缀
     */
    public static final String TOKEN_REFRESH_CACHE_PREFIX = "wword:auth:user:token:refresh.token.";

    public static final String ACCESS_TOKEN_CACHE_PREFIX = "wword:auth:user:token:access_token.";

    public static final String REFRESH_TOKEN_CACHE_PREFIX = "wword:auth:user:token:refresh_token.";

}
