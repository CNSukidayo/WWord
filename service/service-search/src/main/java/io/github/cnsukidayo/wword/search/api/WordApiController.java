package io.github.cnsukidayo.wword.search.api;

import io.github.cnsukidayo.wword.model.entity.es.WordES;
import io.github.cnsukidayo.wword.model.param.AddWordESParam;
import io.github.cnsukidayo.wword.model.params.SearchWordParam;
import io.github.cnsukidayo.wword.search.service.WordESService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/9/10 22:55
 */
@Tag(name = "ES单词管理接口")
@RestController
@RequestMapping("/remote/es/word")
public class WordApiController {

    private final WordESService wordESService;

    public WordApiController(WordESService wordESService) {
        this.wordESService = wordESService;
    }

    @Operation(summary = "添加一个单词到ES中")
    @PostMapping("save")
    public void save(@Valid @RequestBody AddWordESParam addWordESParam) {
        wordESService.save(addWordESParam);
    }

    @Operation(summary = "批量添加单词到ES中")
    @PostMapping("saveBatch")
    public void saveBatch(@Valid @RequestBody List<AddWordESParam> addWordESParams) {
        wordESService.saveBatch(addWordESParams);
    }

    @Operation(summary = "模糊查询单词,这个方法只提供单词的基本信息")
    @PostMapping("searchWord")
    public Page<WordES> searchWord(@Valid @RequestBody SearchWordParam searchWordParam) {
        return wordESService.searchWord(searchWordParam);
    }

    @Operation(summary = "删除某个语种下的所有单词")
    @PostMapping("removeLanguage")
    public void removeLanguage(@RequestParam("languageId") Long languageId) {
        wordESService.removeLanguage(languageId);
    }

}
