package io.github.cnsukidayo.wword.model.vo;

import io.github.cnsukidayo.wword.model.dto.LoginLogDTO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/8/27 15:02
 */
@Schema(description = "登陆日志信息")
public class LoginLogVO extends LoginLogDTO {

    @Schema(description = "登陆类型")
    private String loginTypeString;

    public LoginLogVO() {
    }

    public String getLoginTypeString() {
        return loginTypeString;
    }

    public void setLoginTypeString(String loginTypeString) {
        this.loginTypeString = loginTypeString;
    }
}
