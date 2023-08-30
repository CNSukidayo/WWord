package io.github.cnsukidayo.wword.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.Permission;
import io.github.cnsukidayo.wword.model.entity.Role;
import io.github.cnsukidayo.wword.model.entity.RolePermission;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.params.*;

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
     * 根据一个角色的id删除角色
     *
     * @param roleId 角色的id不为null
     */
    void removeRoleById(Long roleId);

    /**
     * 分页查询所有角色并按照优先级排序
     *
     * @param pageQueryParam 分页查询参数不为null
     * @return 返回分页查询的结果
     */
    IPage<Role> selectRoleByPage(PageQueryParam pageQueryParam);

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

    /**
     * 分页查询一个角色的所有接口
     *
     * @param roleId         角色id不为null
     * @param pageQueryParam 分页查询参数不为null
     * @return 返回分页查询的结果不为null
     */
    IPage<Permission> rolePermissionPage(Long roleId, PageQueryParam pageQueryParam);

    /**
     * 为一个用户分配多个角色
     *
     * @param userRoleParam 用户角色参数不为null
     */
    void grantUserRole(UserRoleParam userRoleParam);

    /**
     * 撤销一个用户的所有角色
     *
     * @param UUID 用户id不为null
     */
    void revokeUserRole(Long UUID);

    /**
     * 分页查询一个用户的所有角色
     *
     * @param UUID           用户id不为null
     * @param pageQueryParam 分页查询参数不为null
     * @return 查询的分页结果不为null
     */
    IPage<Role> selectUserRoleByPage(Long UUID, PageQueryParam pageQueryParam);

    /**
     * 将当前用户的权限克隆给一个目标用户<br>
     * 并且当前接口也可以作为一个角色的接口权限,如果当前接口本身被定义给了一个角色<br>
     * 则代表该角色可以传播它自身的所有权限!当前也包括传播克隆接口
     *
     * @param user          当前用户不能为null
     * @param userRoleParam 待克隆的参数不能为null
     */
    void cloneBatch(User user, UserRoleParam userRoleParam);

}
