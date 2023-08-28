package io.github.cnsukidayo.wword.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.Role;
import io.github.cnsukidayo.wword.model.entity.RolePermission;
import io.github.cnsukidayo.wword.model.params.RoleParam;
import io.github.cnsukidayo.wword.model.params.RolePermissionParam;

/**
 * @author sukidayo
 * @date 2023/8/28 16:41
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 添加一个角色
     *
     * @param roleParam 角色参数不为null
     */
    void saveRole(RoleParam roleParam);

    /**
     * 更新一个角色的信息
     *
     * @param roleId    角色id不为null
     * @param roleParam 角色信息参数不为null
     */
    void updateRole(Long roleId, RoleParam roleParam);

    /**
     * 根据一个角色的id进行删除
     *
     * @param roleId 角色的id不为null
     */
    void removeRoleById(Long roleId);

    /**
     * 分页查询所有角色并按照优先级排序
     *
     * @param pageParam 分页查询参数不为null
     * @return 返回分页查询的结果
     */
    IPage<Role> selectRoleByPage(Page<Role> pageParam);

    /**
     * 为一个角色分配多个权限接口
     *
     * @param rolePermissionParam 角色权限分配参数不为null
     */
    void grantRolePermission(RolePermissionParam rolePermissionParam);

    /**
     * 删除一个角色的所有权限
     *
     * @param roleId 角色id不能为null
     */
    void revokeRolePermissionById(Long roleId);
}
