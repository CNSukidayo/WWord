package io.github.cnsukidayo.wword.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.cnsukidayo.wword.auth.service.RolePermissionService;
import io.github.cnsukidayo.wword.model.dto.PermissionDTO;
import io.github.cnsukidayo.wword.model.dto.RoleDTO;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.params.PageQueryParam;
import io.github.cnsukidayo.wword.model.params.RoleParam;
import io.github.cnsukidayo.wword.model.params.RolePermissionParam;
import io.github.cnsukidayo.wword.model.params.UserRoleParam;
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
@RequestMapping("/api/auth/role_permission")
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @Operation(summary = "添加一个角色")
    @PostMapping("save_role")
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
    @GetMapping("remove_role")
    public void removeRole(@RequestParam("roleId") Long roleId) {
        rolePermissionService.removeRoleById(roleId);
    }

    @Operation(summary = "分页查询所有角色并按照优先级排序")
    @PostMapping("role_page")
    public IPage<RoleDTO> rolePage(@Valid @RequestBody PageQueryParam pageQueryParam) {
        return rolePermissionService.selectRoleByPage(pageQueryParam)
            .convert(role -> new RoleDTO().convertFrom(role));
    }

    @Operation(summary = "为一个角色分配多个权限接口")
    @PostMapping("grant_permission")
    public void grantPermission(@Valid @RequestBody RolePermissionParam rolePermissionParam) {
        rolePermissionService.grantRolePermission(rolePermissionParam);
    }

    @Operation(summary = "删除一个角色的所有接口权限")
    @GetMapping("revoke_permission/{roleId}")
    public void revokePermission(@PathVariable("roleId") Long roleId) {
        rolePermissionService.revokeRolePermissionById(roleId);
    }

    @Operation(summary = "分页查询一个角色的所有接口")
    @PostMapping("role_permission_page/{roleId}")
    public IPage<PermissionDTO> rolePermissionPage(@PathVariable("roleId") Long roleId,
                                                   @Valid @RequestBody PageQueryParam pageQueryParam) {
        return rolePermissionService.rolePermissionPage(roleId, pageQueryParam)
            .convert(permission -> new PermissionDTO().convertFrom(permission));
    }

    @Operation(summary = "为一个用户分配多个角色")
    @PostMapping("grant_role")
    public void grantRole(@Valid @RequestBody UserRoleParam userRoleParam) {
        rolePermissionService.grantUserRole(userRoleParam);
    }

    @Operation(summary = "删除一个用户的所有角色")
    @GetMapping("revoke_role/{uuid}")
    public void revokeRole(@PathVariable("uuid") Long uuid) {
        rolePermissionService.revokeUserRole(uuid);
    }


    @Operation(summary = "分页查询一个用户的所有角色")
    @PostMapping("user_role_page/{uuid}")
    public IPage<RoleDTO> userRolePage(@PathVariable("uuid") Long uuid,
                                       @Valid @RequestBody PageQueryParam pageQueryParam) {
        return rolePermissionService.selectUserRoleByPage(uuid, pageQueryParam)
            .convert(role -> new RoleDTO().convertFrom(role));
    }

    @Operation(summary = "将当前用户的权限克隆给一个用户")
    @PostMapping("clone_batch")
    public void cloneBatch(User user,
                           @Valid @RequestBody UserRoleParam userRoleParam) {
        // todo 功能待完善
        rolePermissionService.cloneBatch(user, userRoleParam);
    }

}
