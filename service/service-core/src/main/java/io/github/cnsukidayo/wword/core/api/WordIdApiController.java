package io.github.cnsukidayo.wword.core.api;

import io.github.cnsukidayo.wword.core.service.WordIdService;
import io.github.cnsukidayo.wword.model.entity.WordId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/9/10 14:21
 */
@Tag(name = "单词-ID管理接口")
@RestController
@RequestMapping("/remote/u/wordId")
public class WordIdApiController {

    private final WordIdService wordIdService;

    public WordIdApiController(WordIdService wordIdService) {
        this.wordIdService = wordIdService;
    }

    @Operation(summary = "查询某个父划分下的有哪些单词")
    @GetMapping("selectWordIdByDivideId")
    public List<WordId> selectWordIdByDivideId(@Parameter(description = "父划分id") @RequestParam("divideId") Long divideId) {
        return wordIdService.selectWordIdByDivideId(divideId);
    }

    @Operation(summary = "查询比当前划分大的并且单词相同的WordId")
    @PostMapping("selectSameWordIdWord")
    public List<WordId> selectSameWordIdWord(@RequestBody WordId wordId) {
        return wordIdService.selectSameWordIdWord(wordId);
    }

    @Operation(summary = "添加一个WordId")
    @PostMapping("saveWordId")
    public WordId saveWordId(@Parameter(description = "待添加的WordId") @RequestBody WordId wordId) {
        wordIdService.save(wordId);
        return wordId;
    }

    @Operation(summary = "判断一个单词是否在一个划分中")
    @GetMapping("exist")
    public Boolean exist(@Parameter(description = "单词原文") @RequestParam("word") String word,
                         @Parameter(description = "划分id") @RequestParam("divideId") Long divideId) {
        return wordIdService.exist(word, divideId);
    }
}
