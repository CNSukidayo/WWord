package io.github.cnsukidayo.wword.model.vo;

import io.github.cnsukidayo.wword.model.dto.PermissionDTO;
import io.github.cnsukidayo.wword.model.enums.PermissionStatus;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/8/28 13:02
 */
@Schema(description = "接口权限对象,用于展示权限接口的情况")
public class PermissionVO extends PermissionDTO {

    @Schema(description = "当前接口权限的状态")
    private PermissionStatus permissionStatus;

    public PermissionVO() {
    }

    public PermissionStatus getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(PermissionStatus permissionStatus) {
        this.permissionStatus = permissionStatus;
    }
}
