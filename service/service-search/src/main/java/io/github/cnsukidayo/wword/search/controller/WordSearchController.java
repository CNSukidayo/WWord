package io.github.cnsukidayo.wword.search.controller;

import io.github.cnsukidayo.wword.model.entity.es.WordES;
import io.github.cnsukidayo.wword.model.params.SearchWordParam;
import io.github.cnsukidayo.wword.search.service.WordESService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/9/14 18:43
 */
@Tag(name = "ES单词管理接口")
@RestController
@RequestMapping("/api/es/word")
public class WordSearchController {

    public final WordESService wordESService;

    public WordSearchController(WordESService wordESService) {
        this.wordESService = wordESService;
    }

    @Operation(summary = "模糊查询单词,这个方法只提供单词的基本信息")
    @PostMapping("searchWord")
    public Page<WordES> searchWord(@Valid @RequestBody SearchWordParam searchWordParam) {
        return wordESService.searchWord(searchWordParam);
    }

}
