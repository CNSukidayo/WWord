package io.github.cnsukidayo.wword.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cnsukidayo.wword.model.entity.Permission;
import io.github.cnsukidayo.wword.model.entity.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/8/28 16:43
 */
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 给一个角色批量授权接口权限
     *
     * @param roleId           角色id不能为null
     * @param permissionIdList 接口权限id列表不能为空
     */
    void grantRolePermission(@Param("roleId") Long roleId, @Param("permissionIdList") List<Long> permissionIdList);


    /**
     * 分页查询
     *
     * @param queryPage 分页查询条件参数
     * @param roleId    角色id
     * @return 返回分页查询的结果对象
     */
    IPage<Permission> rolePermissionPage(Page<RolePermission> queryPage, @Param("roleId") Long roleId);
}
