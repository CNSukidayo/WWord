package io.github.cnsukidayo.wword.auth.api;

import io.github.cnsukidayo.wword.auth.service.UserService;
import io.github.cnsukidayo.wword.model.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

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

    @GetMapping("getById/{uuid}")
    public User getById(@PathVariable("uuid") Serializable uuid) {
        return userService.getById(uuid);
    }

}
