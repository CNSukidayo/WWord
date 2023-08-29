package io.github.cnsukidayo.wword.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.auth.dao.RoleMapper;
import io.github.cnsukidayo.wword.auth.dao.RolePermissionMapper;
import io.github.cnsukidayo.wword.auth.service.PermissionService;
import io.github.cnsukidayo.wword.auth.service.RolePermissionService;
import io.github.cnsukidayo.wword.common.exception.AlreadyExistsException;
import io.github.cnsukidayo.wword.common.exception.NonExistsException;
import io.github.cnsukidayo.wword.model.entity.Permission;
import io.github.cnsukidayo.wword.model.entity.Role;
import io.github.cnsukidayo.wword.model.entity.RolePermission;
import io.github.cnsukidayo.wword.model.params.PageQueryParam;
import io.github.cnsukidayo.wword.model.params.RoleParam;
import io.github.cnsukidayo.wword.model.params.RolePermissionParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/8/28 16:43
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService {

    private final PermissionService permissionService;

    private final RoleMapper roleMapper;


    public RolePermissionServiceImpl(PermissionService permissionService,
                                     RoleMapper roleMapper) {
        this.permissionService = permissionService;
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

        // 首先删除一个角色的所有权限
        revokeRolePermissionById(roleId);
        // 删除该角色
        roleMapper.deleteById(roleId);
    }

    @Override
    public IPage<Role> selectRoleByPage(PageQueryParam pageQueryParam) {
        Assert.notNull(pageQueryParam, "pageQueryParam must not be null");

        Page<Role> page = new Page<>(pageQueryParam.getCurrent(), pageQueryParam.getSize());

        return roleMapper.selectPage(page, new LambdaQueryWrapper<Role>().orderByAsc(Role::getPriority));
    }

    @Override
    public void grantRolePermission(RolePermissionParam rolePermissionParam) {
        Assert.notNull(rolePermissionParam, "rolePermissionParam must not be null");

        // 首先删除一个角色的所有权限接口
        revokeRolePermissionById(rolePermissionParam.getRoleId());
        // 检查要分配的权限id是否存在,查询出所有的权限接口id;然后再判断接口列表是否在集合中
        Set<Long> permissionIdSet = permissionService.getTraces()
            .stream()
            .map(Permission::getId)
            .collect(Collectors.toSet());

        if (!permissionIdSet.containsAll(rolePermissionParam.getPermissionIds())) {
            throw new NonExistsException("目标权限接口不存在!");
        }
        // todo 授权之前还必须检查目标权限必须是当前用户拥有的权限
        // 授权,并且还要对目标用户上传的接口权限做去重
        baseMapper.grantRolePermission(rolePermissionParam.getRoleId(),
            rolePermissionParam.getPermissionIds().stream().distinct().collect(Collectors.toList()));
    }

    @Override
    public void revokeRolePermissionById(Long roleId) {
        Assert.notNull(roleId, "roleId must not be null");

        baseMapper.delete(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId));
    }

    @Override
    public IPage<Permission> rolePermissionPage(Long roleId, PageQueryParam pageQueryParam) {
        Assert.notNull(roleId, "roleId must not be null");
        Assert.notNull(pageQueryParam, "pageQueryParam must not be null");

        return baseMapper.rolePermissionPage(new Page<>(pageQueryParam.getCurrent(), pageQueryParam.getSize()), roleId);
    }


}
