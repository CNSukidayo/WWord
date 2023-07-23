package io.github.cnsukidayo.wword.security.token;

/**
 * 验证token,双token
 *
 * @author sukidayo
 * @date 2023/5/19 16:41
 */
public class AuthToken {

    /**
     * 验证token
     */
    private String accessToken;

    /**
     * 过期时间
     */
    private int expiredIn;

    /**
     * 刷新token
     */
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(int expiredIn) {
        this.expiredIn = expiredIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
