package io.github.cnsukidayo.wword.core.controller;

import io.github.cnsukidayo.wword.common.annotation.ValidList;
import io.github.cnsukidayo.wword.core.service.WordCategoryService;
import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.entity.WordCategory;
import io.github.cnsukidayo.wword.model.params.WordCategoryParam;
import io.github.cnsukidayo.wword.model.params.WordCategoryWordParam;
import io.github.cnsukidayo.wword.model.validate.CreateCheck;
import io.github.cnsukidayo.wword.model.validate.UpdateCheck;
import io.github.cnsukidayo.wword.model.vo.WordCategoryDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/5/17 20:30
 */
@Tag(name = "单词收藏管理接口")
@RestController
@RequestMapping("/api/u/wordCategory")
public class WordCategoryController {

    private final WordCategoryService wordCategoryService;

    public WordCategoryController(WordCategoryService wordCategoryService) {
        this.wordCategoryService = wordCategoryService;
    }

    @Operation(summary = "创建一个单词收藏夹")
    @PostMapping("save")
    public WordCategory save(@RequestBody @Validated(CreateCheck.class) WordCategoryParam addWordCategoryParam,
                             User user) {
        return this.wordCategoryService.save(addWordCategoryParam.convertTo(), user.getUuid());
    }

    @Operation(summary = "删除某个单词收藏夹")
    @GetMapping("remove")
    public void remove(@Parameter(description = "删除某个单词收藏夹") @RequestParam("id") Long id,
                       User user) {
        this.wordCategoryService.remove(id, user.getUuid());
    }

    @Operation(summary = "更新单词收藏夹列表的信息;可以更新顺序/收藏夹的信息")
    @PostMapping("update")
    public void update(@RequestBody @ValidList(UpdateCheck.class) List<WordCategoryParam> addWordCategoryParams,
                       User user) {
        List<WordCategory> param = addWordCategoryParams.stream()
            .map(InputConverter::convertTo)
            .collect(Collectors.toList());
        this.wordCategoryService.update(param, user.getUuid());
    }

    @Operation(summary = "查询当前用户的所有的单词收藏夹")
    @GetMapping("list")
    public List<WordCategoryDetailVO> list(User user) {
        return wordCategoryService.getWordCategoryListAndDetail(user.getUuid());
    }

    @Operation(summary = "添加单词到收藏夹中")
    @GetMapping("addWord")
    public void addWord(@Parameter(description = "单词收藏夹的id") @RequestParam("wordCategoryId") Long wordCategoryId,
                        @Parameter(description = "单词的id") @RequestParam("wordId") Long wordId,
                        User user) {
        wordCategoryService.addWord(wordCategoryId, wordId, user.getUuid());
    }

    @Operation(summary = "移除收藏夹中的某个单词")
    @GetMapping("removeWord")
    public void removeWord(@Parameter(description = "单词收藏夹的id") @RequestParam("wordCategoryId") Long wordCategoryId,
                           @Parameter(description = "收藏夹内单词的id") @RequestParam("wordId") Long wordId,
                           User user) {
        wordCategoryService.removeWord(wordCategoryId, wordId, user.getUuid());
    }

    @Operation(summary = "更改单词在收藏夹中的顺序")
    @PostMapping("updateWordOrder")
    public void updateWordOrder(@RequestBody @Valid List<WordCategoryWordParam> wordCategoryWordList,
                                User user) {
        wordCategoryService.updateWordOrder(
            wordCategoryWordList.stream()
                .map(InputConverter::convertTo)
                .collect(Collectors.toList()),
            user.getUuid());
    }


}
