package io.github.cnsukidayo.wword.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.entity.Permission;
import org.springframework.stereotype.Repository;

/**
 * @author sukidayo
 * @date 2023/8/27 21:47
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {
}
