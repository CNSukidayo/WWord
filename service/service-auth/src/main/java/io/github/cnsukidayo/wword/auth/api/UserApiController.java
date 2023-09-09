package io.github.cnsukidayo.wword.auth.api;

import io.github.cnsukidayo.wword.auth.service.UserService;
import io.github.cnsukidayo.wword.model.bo.UserPermissionBO;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.params.CheckAuthParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/9/7 18:22
 */
@Tag(name = "用户管理接口")
@RestController
@RequestMapping("remote/auth/permission")
@Validated
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "获取并检查token对应的用户是否有目标URL的访问权限")
    @PostMapping("get_and_check")
    public BaseResponse<UserPermissionBO> getById(@Valid @RequestBody CheckAuthParam checkAuthParam) {
        return BaseResponse.ok(userService.getAndAuth(checkAuthParam));
    }

    @Operation(summary = "根据id列表获取所有用户")
    @GetMapping("list_by_ids")
    public List<User> listByIds(@Parameter(description = "用户id集合")
                                @Valid
                                @NotEmpty(message = "id集合不能为空")
                                @RequestBody List<Long> idList) {
        // todo 待校验
        return userService.listByIds(idList);
    }

    @Operation(summary = "根据用户id获取用户信息")
    @GetMapping("get_by_id")
    public User getById(@Parameter(description = "用户id") @RequestParam("uuid") Long uuid) {
        return userService.getById(uuid);
    }


}
