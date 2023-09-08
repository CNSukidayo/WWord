package io.github.cnsukidayo.wword.model.bo;

import io.github.cnsukidayo.wword.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/9/7 22:38
 */
@Schema(description = "用户权限校验业务Bean")
public class UserPermissionBO {

    @Schema(description = "当前用户")
    private User user;

    @Schema(description = "当前用户是否有目标接口的权限")
    private boolean hasPermission;

    public UserPermissionBO() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(boolean hasPermission) {
        this.hasPermission = hasPermission;
    }
}
