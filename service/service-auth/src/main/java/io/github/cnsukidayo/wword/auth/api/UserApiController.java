package io.github.cnsukidayo.wword.auth.api;

import io.github.cnsukidayo.wword.auth.service.UserService;
import io.github.cnsukidayo.wword.model.bo.UserPermissionBO;
import io.github.cnsukidayo.wword.model.params.CheckAuthParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author sukidayo
 * @date 2023/9/7 18:22
 */
@Tag(name = "用户管理接口")
@RestController
@RequestMapping("remote/auth/permission")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("getAndCheck")
    public BaseResponse<UserPermissionBO> getById(@Valid @RequestBody CheckAuthParam checkAuthParam) {
        return BaseResponse.ok(userService.getAndAuth(checkAuthParam));
    }

}
