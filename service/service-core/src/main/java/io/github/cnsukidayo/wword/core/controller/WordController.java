package io.github.cnsukidayo.wword.core.controller;

import io.github.cnsukidayo.wword.core.service.WordService;
import io.github.cnsukidayo.wword.model.dto.WordDTO;
import io.github.cnsukidayo.wword.model.entity.Word;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

/**
 * @author sukidayo
 * @date 2023/10/31 16:08
 */
@Tag(name = "单词管理接口")
@RestController
@RequestMapping("/api/u/word")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @Operation(summary = "根据一个单词的id查询出单词的详细信息")
    @GetMapping("selectWordById")
    public List<WordDTO> selectWordById(@Parameter(description = "单词的id") @RequestParam("wordId") Long wordId) {
        return wordService.selectWordById(wordId)
            .stream()
            .map((Function<Word, WordDTO>) word -> new WordDTO().convertFrom(word))
            .toList();
    }

    @Operation(summary = "添加一个Word")
    @PostMapping("saveWord")
    public Word saveWord(@Parameter(description = "待添加的单词") @RequestBody Word word) {
        return wordService.saveWord(word);
    }

    @Operation(summary = "查询一个单词的结构数量")
    @GetMapping("countStructure")
    public Long countStructure(@Parameter(description = "单词的id") @RequestParam Long wordId) {
        return wordService.countStructure(wordId);
    }

    @Operation(summary = "查询一个单词的信息数")
    @GetMapping("countValue")
    public Long countValue(@Parameter(description = "单词的id") @RequestParam Long wordId) {
        return wordService.countValue(wordId);
    }


}
