package io.github.cnsukidayo.wword.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cnsukidayo.wword.auth.service.RolePermissionService;
import io.github.cnsukidayo.wword.model.entity.Role;
import io.github.cnsukidayo.wword.model.params.PageQueryParam;
import io.github.cnsukidayo.wword.model.params.RoleParam;
import io.github.cnsukidayo.wword.model.params.RolePermissionParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author sukidayo
 * @date 2023/8/28 15:04
 */
@Tag(name = "角色权限管理接口")
@RestController
@RequestMapping("api/auth/role_permission")
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @Operation(summary = "添加一个角色")
    @PostMapping("role_save")
    public void addRole(@Valid @RequestBody RoleParam roleParam) {
        rolePermissionService.saveRole(roleParam);
    }

    @Operation(summary = "更新一个角色的信息")
    @PostMapping("update_role/{roleId}")
    public void updateRole(@PathVariable("roleId") Long roleId,
                           @Valid @RequestBody RoleParam roleParam) {
        rolePermissionService.updateRole(roleId, roleParam);
    }

    @Operation(summary = "删除一个角色信息")
    @GetMapping("role_remove")
    public void removeRole(@RequestParam("roleId") Long roleId) {
        rolePermissionService.removeRoleById(roleId);
    }

    @Operation(summary = "分页查询所有角色并按照优先级排序")
    @PostMapping("role_page")
    public IPage<Role> rolePage(@Valid @RequestBody PageQueryParam pageQueryParam) {
        return rolePermissionService.selectRoleByPage(new Page<>(pageQueryParam.getCurrent(), pageQueryParam.getSize()));
    }


    @Operation(summary = "为一个角色分配多个权限接口")
    @PostMapping("permission_grant")
    public void grantPermission(@Valid @RequestBody RolePermissionParam rolePermissionParam) {
        rolePermissionService.grantRolePermission(rolePermissionParam);
    }

    @Operation(summary = "删除一个角色的所有权限")
    @PostMapping("permission_revoke/{roleId}")
    public void revokePermission(@PathVariable("roleId") Long roleId) {
        rolePermissionService.revokeRolePermissionById(roleId);
    }


}
