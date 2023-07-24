package io.github.cnsukidayo.wword.params;


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
    @NotBlank
    @Size(max = 255, message = "用户名或邮箱的字符长度不能超过 {max}")
    @Schema(description = "用户名称")
    private String username;

    @NotBlank(message = "登录密码不能为空")
    @Size(max = 100, message = "用户密码字符长度不能超过 {max}")
    @Schema(description = "密码")
    private String password;

    public LoginParam() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}