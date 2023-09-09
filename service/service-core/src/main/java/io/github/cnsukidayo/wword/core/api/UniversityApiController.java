package io.github.cnsukidayo.wword.core.api;

import io.github.cnsukidayo.wword.core.service.UniversityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/9/8 15:00
 */
@Tag(name = "学校管理接口")
@RestController
@RequestMapping("/remote/u/university")
public class UniversityApiController {

    private final UniversityService universityService;

    public UniversityApiController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @Operation(summary = "判断是否存在一个学校")
    @GetMapping("has_university")
    public Boolean hasUniversity(@Parameter(description = "学校名称") @RequestParam("school_name") String schoolName) {
        return universityService.hasUniversity(schoolName);
    }

}
