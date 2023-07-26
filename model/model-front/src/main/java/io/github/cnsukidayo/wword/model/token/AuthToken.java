package io.github.cnsukidayo.wword.model.token;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 验证token,双token
 *
 * @author sukidayo
 * @date 2023/5/19 16:41
 */
@Schema(description = "登陆成功后返回的令牌对象")
public class AuthToken {

    @Schema(description = "请求(验证)令牌")
    private String accessToken;

    @Schema(description = "请求令牌过期时间")
    private int expiredIn;

    @Schema(description = "刷新令牌")
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
