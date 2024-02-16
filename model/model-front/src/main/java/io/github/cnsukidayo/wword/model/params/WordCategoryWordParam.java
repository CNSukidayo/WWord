package io.github.cnsukidayo.wword.model.params;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.WordCategoryWord;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * @author sukidayo
 * @date 2023/7/27 9:45
 */
@Schema(description = "单词收藏夹中关联单词请求参数")
public class WordCategoryWordParam implements InputConverter<WordCategoryWord> {

    @Schema(description = "单词收藏夹id")
    @NotNull(message = "收藏夹id不能为空")
    private Long wordCategoryId;

    @Schema(description = "收藏夹内的单词Id")
    @NotNull(message = "收藏夹内的单词Id不能为null")
    private Long wordId;

    @Schema(description = "单词在收藏夹中的排序值")
    @NotNull(message = "单词的排序值未指定!")
    private Integer wordOrder;

    public WordCategoryWordParam() {
    }

    public Long getWordCategoryId() {
        return wordCategoryId;
    }

    public void setWordCategoryId(Long wordCategoryId) {
        this.wordCategoryId = wordCategoryId;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public Integer getWordOrder() {
        return wordOrder;
    }

    public void setWordOrder(Integer wordOrder) {
        this.wordOrder = wordOrder;
    }
}
