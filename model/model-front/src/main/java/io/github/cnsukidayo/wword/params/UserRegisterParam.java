package io.github.cnsukidayo.wword.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * 流程应该是先创建账号->后端生成默认信息->用户登录->登录之后更新个人信息.
 *
 * @author sukidayo
 * @date 2023/7/25 10:55
 */
@Schema(description = "注册用户账号的请求参数")
public class UserRegisterParam {

    @Schema(description = "用户名参数")
    @Min(value = 6, message = "用户名或邮箱长度不小于 {Min}")
    @Max(value = 128, message = "用户名或邮箱长度不超过 {Max}")
    private String account;

    @Schema(description = "密码参数")
    @Min(value = 8, message = "密码长度不能小于 {Min}")
    @Max(value = 128, message = "密码长度不能超过 {Max}")
    private String password;

    @Schema(description = "确认密码参数")
    @Min(value = 8, message = "密码长度不能小于 {Min}")
    @Max(value = 128, message = "密码长度不能超过 {Max}")
    private String confirmPassword;

    public UserRegisterParam() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
