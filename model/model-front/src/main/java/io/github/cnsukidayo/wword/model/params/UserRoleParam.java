package io.github.cnsukidayo.wword.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * @author sukidayo
 * @date 2023/8/29 19:02
 */
@Schema(description = "用户角色参数")
public class UserRoleParam {

    @Schema(description = "用户的id")
    @NotNull(message = "用户id不能为空")
    private Long UUID;

}
