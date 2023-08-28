package io.github.cnsukidayo.wword.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.auth.dao.PermissionMapper;
import io.github.cnsukidayo.wword.auth.service.PermissionService;
import io.github.cnsukidayo.wword.model.entity.Permission;
import io.github.cnsukidayo.wword.model.params.PermissionParam;
import io.github.cnsukidayo.wword.model.vo.PermissionVO;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/8/27 21:47
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {


    @Override
    public Map<String, Map<String, List<PermissionVO>>> getTraceByGroup() {
        List<Permission> permissionList = Optional.ofNullable(getTraces()).orElseGet(ArrayList::new);
        // 进行分组
        // todo 设置当前接口权限的状态信息
        Map<String, Map<String, List<PermissionVO>>> traceGroup = permissionList.stream()
            .collect(Collectors.groupingBy(Permission::getMould,
                Collectors.groupingBy(Permission::getInterfaces,
                    Collectors.mapping(permission -> new PermissionVO().convertFrom(permission),
                        Collectors.toList()))));
        return traceGroup;
    }

    // todo 添加缓存注解
    @Override
    public List<Permission> getTraces() {
        return baseMapper.selectList(null);
    }

    @Override
    public void update(Long permissionId, PermissionParam permissionParam) {
        Assert.notNull(permissionId, "permissionId must not be null");
        Assert.notNull(permissionParam, "permissionParam must not be null");

        Permission permission = permissionParam.convertTo();
        permission.setId(permissionId);
        baseMapper.updateById(permission);
    }


}
