package io.github.cnsukidayo.wword.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/8/29 19:02
 */
@Schema(description = "用户角色参数")
public class UserRoleParam {

    @Schema(description = "用户的id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "角色id列表")
    @NotEmpty(message = "角色id列表不能为空")
    private List<Long> roleIdList;

    public UserRoleParam() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
