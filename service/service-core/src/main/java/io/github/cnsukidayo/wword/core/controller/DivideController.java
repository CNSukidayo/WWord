package io.github.cnsukidayo.wword.core.controller;

import io.github.cnsukidayo.wword.core.service.DivideService;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.model.dto.DivideDTO;
import io.github.cnsukidayo.wword.model.dto.DivideWordDTO;
import io.github.cnsukidayo.wword.model.dto.LanguageClassDTO;
import io.github.cnsukidayo.wword.model.dto.WordDTO;
import io.github.cnsukidayo.wword.model.entity.DivideWord;
import io.github.cnsukidayo.wword.model.entity.LanguageClass;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.entity.Word;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.model.params.AddDivideParam;
import io.github.cnsukidayo.wword.model.params.WordIdFromOtherParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/7/28 14:22
 */
@Tag(name = "划分列表管理接口")
@RestController
@RequestMapping("/api/u/word_divide")
public class DivideController {

    private final DivideService divideService;

    public DivideController(DivideService divideService) {
        this.divideService = divideService;
    }

    @Operation(summary = "查询所有语种")
    @GetMapping("listLanguage")
    public List<LanguageClassDTO> listLanguage() {
        return divideService.listLanguage()
            .stream()
            .map((Function<LanguageClass, LanguageClassDTO>) languageClass -> new LanguageClassDTO().convertFrom(languageClass))
            .collect(Collectors.toList());
    }

    @Operation(summary = "查询某个人的所有划分")
    @GetMapping("listDivide")
    public List<DivideDTO> listDivide(@Parameter(description = "语种id") @RequestParam("languageId") Long languageId,
                                      @Parameter(description = "用户UUID") @RequestParam("uuid") Long UUID) {
        return divideService.listDivide(languageId, UUID);
    }

    @Operation(summary = "查询某些子划分下中定义的所有单词(摘要信息)")
    @PostMapping("listDivideWord")
    public List<DivideWordDTO> listDivideWord(@Parameter(description = "划分id") @RequestBody @Valid List<Long> divideIds) {
        if (CollectionUtils.isEmpty(divideIds)) {
            throw new BadRequestException(ResultCodeEnum.COLLECTION_EMPTY);
        }
        return divideService.listDivideWord(divideIds)
            .stream()
            .map((Function<DivideWord, DivideWordDTO>) divideWord -> new DivideWordDTO().convertFrom(divideWord))
            .collect(Collectors.toList());
    }

    @Operation(summary = "查询某些子划分下中定义的所有单词(单词的详细信息)")
    @PostMapping("listWordByDivideId")
    public Map<Long, List<WordDTO>> listWordByDivideId(@Parameter(description = "划分id") @RequestBody @Valid List<Long> divideIds) {
        if (CollectionUtils.isEmpty(divideIds)) {
            throw new BadRequestException(ResultCodeEnum.COLLECTION_EMPTY);
        }
        return divideService.listWordByDivideId(divideIds)
            .stream()
            .collect(Collectors.groupingBy(Word::getWordId,
                Collectors.mapping(word -> new WordDTO().convertFrom(word), Collectors.toList())));
    }

    @Operation(summary = "添加一个划分")
    @PostMapping("save")
    public void saveDivide(@RequestBody @Valid AddDivideParam addDivideParam, User user) {
        divideService.save(addDivideParam, user.getUuid());
    }

    @Operation(summary = "删除一个划分")
    @PostMapping("remove")
    public void remove(@Parameter(description = "划分的id,可以是父划分id也可以是子划分id") @RequestParam("id") Long id, User user) {
        divideService.remove(id, user.getUuid());
    }

    @Operation(summary = "批量定义一个单词到一个父划分中,从某个父划分中得到这些单词的定义")
    @PostMapping("batchDefineWordFromOtherDivide")
    public void batchDefineWordFromOtherDivide(@RequestBody WordIdFromOtherParam wordIdFromOtherParam, User user) {
        divideService.batchDefineWordFromOtherDivide(wordIdFromOtherParam, user.getUuid());
    }


    @Operation(summary = "批量添加单词到一个子划分中")
    @PostMapping("saveDivideWord")
    public void saveDivideWord(@Parameter(description = "划分的id") @RequestParam("divideId") Long divideId,
                               @Parameter(description = "单词id列表") @NotEmpty(message = "单词id列表不为空") @RequestBody List<Long> wordIdList,
                               User user) {
        divideService.saveBatchDivideWord(divideId, wordIdList, user.getUuid());
    }

    @Operation(summary = "批量删除一个子划分中的单词")
    @PostMapping("deleteDivideWord")
    public void deleteDivideWord(@Parameter(description = "划分的id") @RequestParam("divideId") Long divideId,
                                 @Parameter(description = "单词id列表") @NotEmpty(message = "单词id列表不为空") @RequestBody List<Long> wordIdList,
                                 User user) {
        divideService.deleteDivideWord(divideId, wordIdList, user.getUuid());
    }

    @Operation(summary = "拷贝一个划分")
    @PostMapping("copyDivide")
    public void copyDivide(@Parameter(description = "划分的id") @RequestParam("divideId") Long divideId,
                           User user) {
        divideService.copyDivide(divideId, user.getUuid());
    }


}
