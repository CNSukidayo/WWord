package io.github.cnsukidayo.wword.params;


import io.swagger.v3.oas.annotations.media.Schema;
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
    @Size(min = 6, max = 128, message = "用户名或邮箱长度不小于 {min} 且长度不超过 {max} ")
    private String account;

    @Schema(description = "密码参数")
    @Size(min = 8, max = 128, message = "密码长度不能小于 {min} 且长度不超过 {max} ")
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
