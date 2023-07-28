package io.github.cnsukidayo.wword.core.controller;

import io.github.cnsukidayo.wword.core.service.WordDivideService;
import io.github.cnsukidayo.wword.model.dto.LanguageClassDTO;
import io.github.cnsukidayo.wword.model.dto.WordDivideDTO;
import io.github.cnsukidayo.wword.model.pojo.LanguageClass;
import io.github.cnsukidayo.wword.model.pojo.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/28 14:22
 */
@Tag(name = "划分列表管理接口")
@RestController
@RequestMapping("/api/u/word_divide")
public class WordDivideController {

    private final WordDivideService wordDivideService;

    public WordDivideController(WordDivideService wordDivideService) {
        this.wordDivideService = wordDivideService;
    }

    @Operation(summary = "查询所有语种")
    @GetMapping("listLanguage")
    public List<LanguageClassDTO> listLanguage() {
        List<LanguageClass> languageClassList = wordDivideService.listLanguage();
        List<LanguageClassDTO> result = new ArrayList<>(languageClassList.size());
        languageClassList.forEach(languageClass -> result.add(languageClass.convertToDTO(new LanguageClassDTO())));
        return result;
    }

    @Operation(summary = "查询某个语种的官方划分")
    @GetMapping("listOfficialDivide")
    public List<WordDivideDTO> listOfficialDivide(@Parameter(description = "语种id") @RequestParam("languageId") Long languageId) {
        return wordDivideService.listOfficialDivide(languageId, 1L);
    }

    @Operation(summary = "查询某个语种的个人所有划分")
    @GetMapping("listDivide")
    public void listDivide(@Parameter(description = "语种id") @RequestParam("languageId") Long languageId, User user) {

    }

    @Operation(summary = "查询某个划分下的所有单词")
    @GetMapping("listWord")
    public void listWord(@Parameter(description = "划分id") @RequestParam("divideId") Long divideId) {

    }


}
