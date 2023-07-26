package io.github.cnsukidayo.wword.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
 * @author sukidayo
 * @date 2023/7/25 20:36
 */
@Schema(description = "用户更新密码请求体")
public class UpdatePasswordParam {

    @Schema(description = "旧密码")
    @Size(min = 8, max = 128, message = "密码长度不能小于 {min} 且长度不超过 {max} ")
    private String oldPassword;

    @Schema(description = "新密码")
    @Size(min = 8, max = 128, message = "密码长度不能小于 {min} 且长度不超过 {max} ")
    private String newPassword;

    @Schema(description = "确认新密码")
    @Size(min = 8, max = 128, message = "密码长度不能小于 {min} 且长度不超过 {max} ")
    private String confirmNewPassword;

    public UpdatePasswordParam() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
