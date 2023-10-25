package io.github.cnsukidayo.wword.auth.controller;

import io.github.cnsukidayo.wword.auth.service.PermissionService;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.model.entity.Permission;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.model.params.PermissionParam;
import io.github.cnsukidayo.wword.model.vo.PermissionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author sukidayo
 * @date 2023/8/27 18:33
 */
@Tag(name = "权限管理接口")
@RestController
@RequestMapping("/api/auth/permission")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Operation(summary = "跟踪一个接口")
    @PostMapping(value = "trace")
    public void trace(@Valid @RequestBody PermissionParam permissionParam) {
        Permission permission = permissionParam.convertTo();
        permissionService.save(permission);
    }

    @Operation(summary = "分组取得所有跟踪的接口")
    @GetMapping("getTraceByGroup")
    public Map<String, Map<String, List<PermissionVO>>> getTraceByGroup() {
        return permissionService.getTraceByGroup();
    }

    @Operation(summary = "批量撤销接口的跟踪")
    @PostMapping("untrace")
    public void untrace(@Parameter(description = "待取消跟踪的接口的id") @RequestBody List<Long> permissionIdList) {
        if (CollectionUtils.isEmpty(permissionIdList)) {
            throw new BadRequestException(ResultCodeEnum.UNTRACE_ID_LIST_NOT_EMPTY);
        }
        permissionService.removeBatchByIds(permissionIdList);
    }

    @Operation(summary = "更新一个跟踪接口的信息")
    @PostMapping("update_trace/{permissionId}")
    public void updateTrace(@Parameter(description = "跟踪的目标接口id") @PathVariable("permissionId") Long permissionId,
                            @Valid @RequestBody PermissionParam permissionParam) {
        permissionService.update(permissionId, permissionParam);
    }


}
