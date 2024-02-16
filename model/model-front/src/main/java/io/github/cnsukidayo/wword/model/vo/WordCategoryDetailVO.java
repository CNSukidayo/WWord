package io.github.cnsukidayo.wword.model.vo;

import io.github.cnsukidayo.wword.model.dto.WordCategoryDTO;
import io.github.cnsukidayo.wword.model.dto.WordCategoryWordDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/27 10:10
 */
@Schema(description = "单词收藏夹的详情信息")
public class WordCategoryDetailVO extends WordCategoryDTO {

    @Schema(description = "当前收藏夹收藏的所有单词列表信息")
    private List<WordCategoryWordDTO> wordCategoryWordList;

    public WordCategoryDetailVO() {
    }

    public List<WordCategoryWordDTO> getWordCategoryWordList() {
        return wordCategoryWordList;
    }

    public void setWordCategoryWordList(List<WordCategoryWordDTO> wordCategoryWordList) {
        this.wordCategoryWordList = wordCategoryWordList;
    }
}
