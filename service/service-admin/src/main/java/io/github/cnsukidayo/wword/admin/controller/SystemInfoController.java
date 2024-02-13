package io.github.cnsukidayo.wword.admin.controller;

import io.github.cnsukidayo.wword.admin.service.SystemInfoService;
import io.github.cnsukidayo.wword.model.dto.SystemInfoDTO;
import io.github.cnsukidayo.wword.model.dto.support.MPPage;
import io.github.cnsukidayo.wword.model.params.PageQueryParam;
import io.github.cnsukidayo.wword.model.params.SystemInfoParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2024/2/10 7:48
 */
@Tag(name = "系统信息管理接口")
@RestController
@RequestMapping("/api/admin/systemInfo")
public class SystemInfoController {

    private final SystemInfoService systemInfoService;

    public SystemInfoController(SystemInfoService systemInfoService) {
        this.systemInfoService = systemInfoService;
    }

    @Operation(summary = "保存一个系统信息")
    @PostMapping("saveSystemInfo")
    public void saveSystemInfo(@RequestBody SystemInfoParam systemInfoParam) {
        systemInfoService.saveSystemInfo(systemInfoParam);
    }

    @Operation(summary = "分页查询系统信息")
    @PostMapping("listSystemInfo")
    public MPPage<SystemInfoDTO> listSystemInfo(@RequestBody PageQueryParam pageQueryParam) {
        return new MPPage<SystemInfoDTO>().convertFrom(systemInfoService.listSystemInfo(pageQueryParam));
    }

}
