package io.github.cnsukidayo.wword.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.auth.mapper.PermissionMapper;
import io.github.cnsukidayo.wword.auth.service.PermissionService;
import io.github.cnsukidayo.wword.model.entity.Permission;
import org.springframework.stereotype.Service;

/**
 * @author sukidayo
 * @date 2023/8/27 21:47
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}
