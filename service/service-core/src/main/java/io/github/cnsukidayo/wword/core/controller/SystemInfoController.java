package io.github.cnsukidayo.wword.core.controller;

import io.github.cnsukidayo.wword.core.service.SystemInfoService;
import io.github.cnsukidayo.wword.model.enums.SystemInfoType;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2024/2/8 19:17
 */
@Tag(name = "系统信息管理接口")
@RestController
@RequestMapping("/api/u/systemInfo")
public class SystemInfoController {

    private final SystemInfoService systemInfoService;

    public SystemInfoController(SystemInfoService systemInfoService) {
        this.systemInfoService = systemInfoService;
    }

    @Operation(summary = "获取系统信息,返回markdown格式的信息")
    @GetMapping("uncheck/getMessage")
    public BaseResponse<String> getSystemInfo(@Parameter(description = "获取系统信息的Key") @RequestParam("systemInfoType") SystemInfoType systemInfoType) {
        return BaseResponse.ok(systemInfoService.getSystemInfoContext(systemInfoType));
    }

}
