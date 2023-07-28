package io.github.cnsukidayo.wword.core.controller;

import io.github.cnsukidayo.wword.core.service.UniversityService;
import io.github.cnsukidayo.wword.model.dto.UniversityDTO;
import io.github.cnsukidayo.wword.model.pojo.University;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    @GetMapping("getByName/{school_name}")
    public List<UniversityDTO> getByName(@Parameter(description = "学校的名称", required = true) @PathVariable("school_name") String schoolName) {
        List<University> universityList = universityService.getByName(schoolName);
        List<UniversityDTO> universityDTOList = new ArrayList<>(universityList.size());
        universityList.forEach(university -> universityDTOList.add(university.convertToDTO(new UniversityDTO())));
        return universityDTOList;
    }



}
