package io.github.cnsukidayo.wword.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.auth.dao.RoleMapper;
import io.github.cnsukidayo.wword.auth.dao.RolePermissionMapper;
import io.github.cnsukidayo.wword.auth.service.RolePermissionService;
import io.github.cnsukidayo.wword.common.exception.AlreadyExistsException;
import io.github.cnsukidayo.wword.model.entity.Role;
import io.github.cnsukidayo.wword.model.entity.RolePermission;
import io.github.cnsukidayo.wword.model.params.RoleParam;
import io.github.cnsukidayo.wword.model.params.RolePermissionParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author sukidayo
 * @date 2023/8/28 16:43
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService {

    private final RoleMapper roleMapper;

    public RolePermissionServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }


    @Override
    public void saveRole(RoleParam roleParam) {
        Assert.notNull(roleParam, "roleParam must not be null");

        // 首先验证数据库中是否存在该角色
        if (roleMapper.exists(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, roleParam.getRoleName()))) {
            throw new AlreadyExistsException("目标角色已经存在,请勿重复添加!");
        }

        Role role = roleParam.convertTo();
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Long roleId, RoleParam roleParam) {
        Assert.notNull(roleId, "roleId must not be null");
        Assert.notNull(roleParam, "roleParam must not be null");

        Role role = roleParam.convertTo();
        role.setId(roleId);
        roleMapper.updateById(role);

    }

    @Override
    @Transactional
    public void removeRoleById(Long roleId) {
        Assert.notNull(roleId, "roleId must not be null");

        // 首先删除该角色拥有的所有权限
        baseMapper.delete(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getPermissionId, roleId));
        // 删除该角色
        roleMapper.deleteById(roleId);
    }

    @Override
    public IPage<Role> selectRoleByPage(Page<Role> pageParam) {
        Assert.notNull(pageParam, "pageParam must not be null");

        return roleMapper.selectPage(pageParam, new LambdaQueryWrapper<Role>().orderByAsc(Role::getPriority));
    }

    @Override
    public void grantRolePermission(RolePermissionParam rolePermissionParam) {
        Assert.notNull(rolePermissionParam, "rolePermissionParam must not be null");

        // 首先删除一个角色的所有权限接口
        revokeRolePermissionById(rolePermissionParam.getRoleId());

        // 授权
        baseMapper.grantRolePermission(rolePermissionParam.getRoleId(), rolePermissionParam.getPermissionIds());

    }

    @Override
    public void revokeRolePermissionById(Long roleId) {
        Assert.notNull(roleId, "roleId must not be null");

        baseMapper.deleteById(roleId);
    }


}
