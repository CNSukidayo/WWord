package io.github.cnsukidayo.wword.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/8/28 18:22
 */
@Schema(description = "角色权限参数")
public class RolePermissionParam {

    @Schema(description = "角色ID")
    @NotNull(message = "角色ID不为空")
    private Long roleId;

    @Schema(description = "权限接口id列表")
    @NotEmpty(message = "权限接口id列表不能为空")
    private List<Long> permissionIds;

    public RolePermissionParam() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
