package io.github.cnsukidayo.wword.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.entity.base.BaseEntity;

/**
 * @author sukidayo
 * @date 2023/8/29 19:10
 */
@TableName("user_role")
public class UserRole extends BaseEntity {

    @TableField("uuid")
    private Long UUID;

    @TableField("role_id")
    private Long roleId;

    public UserRole() {
    }

    public Long getUUID() {
        return UUID;
    }

    public void setUUID(Long UUID) {
        this.UUID = UUID;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
