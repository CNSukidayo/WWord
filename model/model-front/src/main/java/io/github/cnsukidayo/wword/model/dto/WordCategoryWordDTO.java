package io.github.cnsukidayo.wword.model.dto;

import io.github.cnsukidayo.wword.model.base.OutputConverter;
import io.github.cnsukidayo.wword.model.entity.WordCategoryWord;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/27 10:10
 */
@Schema(description = "用户帖子收藏夹")
public class WordCategoryWordDTO implements OutputConverter<WordCategoryWordDTO, WordCategoryWord> {

    @Schema(description = "当前收藏夹的id")
    private Long wordCategoryId;

    @Schema(description = "当前单词的id")
    private Long wordId;

    @Schema(description = "当前单词在收藏夹中的顺序")
    private Integer wordOrder;

    public WordCategoryWordDTO() {
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
