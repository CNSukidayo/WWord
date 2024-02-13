package io.github.cnsukidayo.wword.core.controller;

import io.github.cnsukidayo.wword.core.service.UniversityService;
import io.github.cnsukidayo.wword.model.dto.UniversityDTO;
import io.github.cnsukidayo.wword.model.entity.University;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/7/26 14:12
 */
@Tag(name = "学校管理接口")
@RestController
@RequestMapping("/api/u/university")
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @Operation(summary = "根据名称查询学校")
    @GetMapping("getByName")
    public List<UniversityDTO> getByName(@Parameter(description = "学校的名称", required = true) @RequestParam("schoolName") String schoolName) {
        return universityService.getByName(schoolName)
                .stream()
                .map((Function<University, UniversityDTO>) university -> new UniversityDTO().convertFrom(university))
                .collect(Collectors.toList());
    }


}
