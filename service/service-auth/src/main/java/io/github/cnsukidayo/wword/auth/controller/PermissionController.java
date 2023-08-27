package io.github.cnsukidayo.wword.auth.controller;

import io.github.cnsukidayo.wword.auth.service.PermissionService;
import io.github.cnsukidayo.wword.model.entity.Permission;
import io.github.cnsukidayo.wword.model.params.PermissionParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/8/27 18:33
 */
@Tag(name = "权限管理接口")
@RestController
@RequestMapping("api/auth/permission")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Operation(summary = "跟踪一个接口")
    @PostMapping("trace")
    public void trace(@Valid @RequestBody PermissionParam permissionParam) {
        Permission permission = permissionParam.convertTo();
        permissionService.save(permission);
    }

}
