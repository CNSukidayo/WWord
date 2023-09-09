package io.github.cnsukidayo.wword.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/8/27 21:47
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色的id列表查询当前角色有权限的所有接口
     *
     * @param roleIdList 角色的id列表不为空
     * @return 返回权限接口集合不为null
     */
    List<Permission> selectPermissionByRoleIdList(@Param("roleIdList") List<Long> roleIdList);


}
