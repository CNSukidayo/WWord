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
    private Long uuid;

    @TableField("role_id")
    private Long roleId;

    public UserRole() {
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
