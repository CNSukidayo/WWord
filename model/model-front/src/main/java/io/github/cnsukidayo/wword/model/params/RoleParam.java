package io.github.cnsukidayo.wword.model.params;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author sukidayo
 * @date 2023/8/28 16:32
 */
@Schema(description = "角色参数")
public class RoleParam implements InputConverter<Role> {

    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不为空")
    @Size(max = 12, message = "角色名称不能超过 {max} 个字符")
    private String roleName;

    @Schema(description = "优先级")
    @NotNull(message = "优先级值不能为空")
    private Integer priority;

    public RoleParam() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
