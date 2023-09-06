package io.github.cnsukidayo.wword.model.params;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 登陆参数
 *
 * @author cnsukidayo
 * @date 2023/5/17 20:14
 */
@Schema(description = "用户登录请求体")
public class LoginParam {
    @Schema(description = "用户名参数")
    @Size(min = 6, message = "用户名或邮箱长度不小于 {min} ")
    @Size(max = 128, message = "用户名长度不能超过 {max}")
    @NotBlank(message = "用户名不能为空")
    private String account;

    @Schema(description = "密码参数")
    @Size(min = 8, message = "密码长度不能小于 {min} ")
    @Size(max = 128, message = "密码长度不能超过 {max}")
    @NotBlank(message = "密码不能为空")
    private String password;

    public LoginParam() {
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
}
