package io.github.cnsukidayo.wword.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.entity.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
    void grantRolePermission(@Param("roleId") Long roleId, @Param("permissionIdList") Long[] permissionIdList);

}
