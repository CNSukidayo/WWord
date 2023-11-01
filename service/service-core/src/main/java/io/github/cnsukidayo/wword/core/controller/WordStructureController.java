package io.github.cnsukidayo.wword.core.controller;

import io.github.cnsukidayo.wword.core.service.WordStructureService;
import io.github.cnsukidayo.wword.model.dto.WordStructureDTO;
import io.github.cnsukidayo.wword.model.entity.WordStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

/**
 * @author sukidayo
 * @date 2023/10/31 16:08
 */
@Tag(name = "单词结构管理接口")
@RestController
@RequestMapping("/api/word/wordStructure")
public class WordStructureController {

    private final WordStructureService wordStructureService;

    public WordStructureController(WordStructureService wordStructureService) {
        this.wordStructureService = wordStructureService;
    }

    @Operation(summary = "查询一个单词的结构信息")
    @GetMapping("selectWordById")
    public List<WordStructureDTO> selectWordStructureById(@Parameter(description = "语种的id") @RequestParam("languageId") Long languageId) {
        return wordStructureService.selectWordStructureById(languageId)
            .stream()
            .map((Function<WordStructure, WordStructureDTO>) wordStructure -> new WordStructureDTO().convertFrom(wordStructure))
            .toList();
    }


}
