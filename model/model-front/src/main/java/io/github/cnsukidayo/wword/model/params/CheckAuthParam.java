package io.github.cnsukidayo.wword.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * @author sukidayo
 * @date 2023/9/7 22:48
 */
@Schema(description = "检查用户权限参数")
public class CheckAuthParam {

    @Schema(description = "待校验的用户token")
    @NotBlank(message = "用户token不能为空")
    private String token;

    @Schema(description = "当前用户请求的目标URL")
    @NotBlank(message = "目标URL不为null")
    private String targetUrl;

    public CheckAuthParam() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
