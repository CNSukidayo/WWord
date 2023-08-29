package io.github.cnsukidayo.wword.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.entity.base.BaseEntity;

/**
 * @author sukidayo
 * @date 2023/8/28 16:42
 */
@TableName("role_permission")
public class RolePermission extends BaseEntity {

    @TableField("role_id")
    private Long roleId;

    @TableField("permission_id")
    private Long permissionId;

    public RolePermission() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
