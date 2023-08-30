package io.github.cnsukidayo.wword.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.auth.dao.RoleMapper;
import io.github.cnsukidayo.wword.auth.dao.RolePermissionMapper;
import io.github.cnsukidayo.wword.auth.dao.UserRoleMapper;
import io.github.cnsukidayo.wword.auth.service.PermissionService;
import io.github.cnsukidayo.wword.auth.service.RolePermissionService;
import io.github.cnsukidayo.wword.common.exception.AlreadyExistsException;
import io.github.cnsukidayo.wword.common.exception.NonExistsException;
import io.github.cnsukidayo.wword.model.entity.*;
import io.github.cnsukidayo.wword.model.params.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/8/28 16:43
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService {

    private Logger log = LoggerFactory.getLogger(getClass());


    private final PermissionService permissionService;

    private final RoleMapper roleMapper;

    private final UserRoleMapper userRoleMapper;

    public RolePermissionServiceImpl(PermissionService permissionService,
                                     RoleMapper roleMapper,
                                     UserRoleMapper userRoleMapper) {
        this.permissionService = permissionService;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
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
    @Transactional
    public void grantRolePermission(RolePermissionParam rolePermissionParam) {
        Assert.notNull(rolePermissionParam, "rolePermissionParam must not be null");

        // 首先删除一个角色的所有权限接口
        revokeRolePermissionById(rolePermissionParam.getRoleId());
        // 检查要分配的权限id是否存在;需要去重
        List<Long> permissionIdList = rolePermissionParam.getPermissionIds()
            .stream()
            .distinct()
            .toList();
        long totalCount = permissionService.count(new LambdaQueryWrapper<Permission>()
            .in(Permission::getId, permissionIdList));
        if (permissionIdList.size() != totalCount) {
            log.error("The target permission ID list [{}] has non-existent IDs", rolePermissionParam);
            throw new NonExistsException("目标权限接口不存在!");
        }
        // todo 授权之前还必须检查目标权限必须是当前用户拥有的权限
        baseMapper.grantRolePermission(rolePermissionParam.getRoleId(), permissionIdList);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void grantUserRole(UserRoleParam userRoleParam) {
        Assert.notNull(userRoleParam, "userRoleParam must not be null");

        // 首先删除一个用户的所有角色
        revokeUserRole(userRoleParam.getUserId());
        // 首选去重
        List<Long> roleIdList = userRoleParam.getRoleIdList()
            .stream()
            .distinct()
            .toList();
        // 检查要分配的角色是否存在
        Long totalCount = roleMapper.selectCount(new LambdaQueryWrapper<Role>()
            .in(Role::getId, roleIdList));

        if (totalCount != roleIdList.size()) {
            log.error("The target role ID list [{}] has non-existent IDs", roleIdList);
            throw new NonExistsException("指定的角色不存在!");
        }

        // 为目标用户添加角色
        baseMapper.grantUserRole(userRoleParam.getUserId(), roleIdList);

    }

    @Override
    @Transactional
    public void revokeUserRole(Long UUID) {
        Assert.notNull(UUID, "UUID must not be null");

        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUUID, UUID));
    }

    @Override
    public IPage<Role> selectUserRoleByPage(Long UUID, PageQueryParam pageQueryParam) {
        Assert.notNull(UUID, "UUID must not be null");
        Assert.notNull(pageQueryParam, "pageQueryParam must not be null");

        return baseMapper.userRolePage(new Page<>(pageQueryParam.getCurrent(), pageQueryParam.getSize()), UUID);
    }

    @Override
    @Transactional
    public void cloneBatch(User user, UserRoleParam userRoleParam) {
        Assert.notNull(user, "user must not be null");
        Assert.notNull(userRoleParam, "userRoleParam must not be null");

        // 首先查询当前用户是否有要克隆的所有角色
        Set<Long> roleIdList = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUUID, user.getUUID()))
            .stream()
            .map(UserRole::getRoleId)
            .collect(Collectors.toSet());

        // 去重
        Set<Long> toCloneRoleIdList = new HashSet<>(userRoleParam.getRoleIdList());

        if (!roleIdList.containsAll(toCloneRoleIdList)) {
            log.error("The target role ID list [{}] has user's role non-existent IDs", toCloneRoleIdList);
        }

        // 查询目标用户当前拥有的角色
        Set<Long> targetUserRoleIdList = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUUID, userRoleParam.getUserId()))
            .stream()
            .map(UserRole::getRoleId)
            .collect(Collectors.toSet());

        toCloneRoleIdList.removeAll(targetUserRoleIdList);
        // 克隆角色
        userRoleParam.setRoleIdList(new ArrayList<>(targetUserRoleIdList));
        grantUserRole(userRoleParam);

    }


}
