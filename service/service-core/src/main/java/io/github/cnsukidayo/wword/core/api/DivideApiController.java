package io.github.cnsukidayo.wword.core.api;

import io.github.cnsukidayo.wword.core.service.DivideService;
import io.github.cnsukidayo.wword.model.entity.Divide;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/9/8 15:00
 */
@Tag(name = "划分列表管理接口")
@RestController
@RequestMapping("/remove/core/divide")
public class DivideApiController {

    private final DivideService divideService;

    public DivideApiController(DivideService divideService) {
        this.divideService = divideService;
    }

    @Operation(summary = "查询出所有的父划分,并且排除用户定义的划分")
    @GetMapping("listParentDivide")
    public List<Divide> listParentDivide() {
        return divideService.listParentDivide();
    }

}
