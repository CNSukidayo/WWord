package io.github.cnsukidayo.wword.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/23 15:25
 */
@Schema(description = "用户个人信息")
public class UserProfileDTO {
    @Schema(description = "用户昵称")
    private String nick;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
