package io.github.cnsukidayo.wword.model.vo;

import io.github.cnsukidayo.wword.model.dto.UserProfileDTO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/27 10:18
 */
@Schema(description = "用户个人信息")
public class UserProfileVO extends UserProfileDTO {

    @Schema(description = "个人性别")
    private String sexString;

    public UserProfileVO() {
    }

    public String getSexString() {
        return sexString;
    }

    public void setSexString(String sexString) {
        this.sexString = sexString;
    }
}
