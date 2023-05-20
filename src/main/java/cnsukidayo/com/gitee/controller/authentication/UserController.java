package cnsukidayo.com.gitee.controller.authentication;

import cnsukidayo.com.gitee.model.params.LoginParam;
import cnsukidayo.com.gitee.model.pojo.User;
import cnsukidayo.com.gitee.security.token.AuthToken;
import cnsukidayo.com.gitee.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * @author sukidayo
 * @date 2023/5/17 20:14
 */

@RestController
@RequestMapping("/api/authentication/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthToken login(@RequestBody LoginParam loginParam) {
        return userService.login(loginParam);
    }

    @GetMapping("/getProfile")
    public User getProfile() {
        return userService.getProfile();
    }


    @PostMapping("refresh/{refreshToken}")
    public AuthToken refresh(@PathVariable("refreshToken") String refreshToken) {
        return userService.refreshToken(refreshToken);
    }

    @PostMapping("logout")
    public void logout() {
        userService.clearToken();
    }

}
