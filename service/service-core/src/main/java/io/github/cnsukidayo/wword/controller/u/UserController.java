package io.github.cnsukidayo.wword.controller.u;

import io.github.cnsukidayo.wword.model.dto.UserProfileDTO;
import io.github.cnsukidayo.wword.model.params.LoginParam;
import io.github.cnsukidayo.wword.model.params.UpdatePasswordParam;
import io.github.cnsukidayo.wword.model.params.UpdateUserParam;
import io.github.cnsukidayo.wword.model.params.UserRegisterParam;
import io.github.cnsukidayo.wword.model.pojo.User;
import io.github.cnsukidayo.wword.service.UserService;
import io.github.cnsukidayo.wword.model.token.AuthToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author sukidayo
 * @date 2023/5/17 20:14
 */
@Tag(name = "用户管理接口")
@RestController
@RequestMapping("/api/u/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "用户注册接口")
    @PostMapping("register")
    public void register(@RequestBody @Valid UserRegisterParam userRegisterParam) {
        userService.register(userRegisterParam);
    }

    @Operation(summary = "用户登录接口")
    @PostMapping("login")
    public AuthToken login(@RequestBody @Valid LoginParam loginParam) {
        return userService.login(loginParam);
    }

    @Operation(summary = "获取用户个人信息接口")
    @GetMapping("getProfile")
    public UserProfileDTO getProfile(User user) {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        BeanUtils.copyProperties(user, userProfileDTO);
        userProfileDTO.setSexString(user.getSex().value());
        return userProfileDTO;
    }

    @Operation(summary = "更新密码")
    @PostMapping("updatePassword")
    public void updatePassword(@RequestBody @Valid UpdatePasswordParam updatePasswordParam,
                               User user) {
        userService.updatePassword(user, updatePasswordParam);
    }

    @Operation(summary = "更新用户个人信息")
    @PostMapping("update")
    public void update(@RequestBody @Valid UpdateUserParam updateUserParam,
                       User user) {
        userService.update(updateUserParam, user);
    }

    @Operation(summary = "刷新客户端令牌")
    @PostMapping("refresh/{refreshToken}")
    public AuthToken refresh(@Parameter(description = "刷新令牌", required = true) @PathVariable("refreshToken") String refreshToken) {
        return userService.refreshToken(refreshToken);
    }

    @Operation(summary = "退出登陆")
    @PostMapping("logout")
    public void logout() {
        userService.clearToken();
    }


}
