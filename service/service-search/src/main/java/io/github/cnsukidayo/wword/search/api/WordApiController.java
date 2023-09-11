package io.github.cnsukidayo.wword.search.api;

import io.github.cnsukidayo.wword.model.entity.es.WordES;
import io.github.cnsukidayo.wword.model.param.AddWordESParam;
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

    @Operation(description = "添加一个单词到ES中")
    @PostMapping("save")
    public void save(@Valid @RequestBody AddWordESParam addWordESParam) {
        wordESService.save(addWordESParam);
    }

    @Operation(description = "批量添加单词到ES中")
    @PostMapping("saveBatch")
    public void saveBatch(@Valid @RequestBody List<AddWordESParam> addWordESParams) {
        wordESService.saveBatch(addWordESParams);
    }

    @Operation(description = "模糊查询单词,这个方法只提供单词的基本信息")
    @PostMapping("searchWord")
    public Page<WordES> searchWord(@Valid @RequestBody SearchWordParam searchWordParam) {
        return wordESService.searchWord(searchWordParam);
    }

}
