package io.github.cnsukidayo.wword.admin.controller;

import io.github.cnsukidayo.wword.admin.dao.WordMapper;
import io.github.cnsukidayo.wword.admin.service.WordHandleService;
import io.github.cnsukidayo.wword.model.params.AddOrUpdateWordParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author sukidayo
 * @date 2023/7/30 21:44
 */
@Tag(name = "单词处理接口")
@RestController
@RequestMapping("/api/admin/word_handle")
public class WordHandleController {

    private final WordHandleService wordHandleService;
    private final WordMapper wordMapper;

    public WordHandleController(WordHandleService wordHandleService,
                                WordMapper wordMapper) {
        this.wordHandleService = wordHandleService;
        this.wordMapper = wordMapper;
    }

    @Operation(summary = "手动添加一个单词")
    @PostMapping("saveWord")
    public void saveWord(@RequestBody @Valid AddOrUpdateWordParam addOrUpdateWordParam) {
        System.out.println(addOrUpdateWordParam);


    }

    @Operation(summary = "处理EnWords")
    @GetMapping("handleEnWords")
    public void handleEnWords() {
        wordHandleService.handleEnWords();
    }


}
