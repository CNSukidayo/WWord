package io.github.cnsukidayo.wword.admin.controller;

import io.github.cnsukidayo.wword.admin.service.WordStructureService;
import io.github.cnsukidayo.wword.model.dto.WordStructureDTO;
import io.github.cnsukidayo.wword.model.pojo.WordStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/7/30 14:10
 */
@Tag(name = "单词结构管理接口")
@RestController
@RequestMapping("/api/admin/word_structure")
public class WordStructureController {

    private final WordStructureService wordStructureService;

    public WordStructureController(WordStructureService wordStructureService) {
        this.wordStructureService = wordStructureService;
    }

    @Operation(summary = "查询某个语种的结构")
    @GetMapping("get")
    public List<WordStructureDTO> get(@Parameter(description = "语种的id") @RequestParam("languageId") Long languageId) {
        return wordStructureService.get(languageId)
                .stream()
                .map((Function<WordStructure, WordStructureDTO>) wordStructure -> new WordStructureDTO().convertFrom(wordStructure))
                .collect(Collectors.toList());
    }


}
