package io.github.cnsukidayo.wword.model.vo;

import io.github.cnsukidayo.wword.model.dto.WordDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 单个单词的详细信息
 *
 * @author sukidayo
 * @date 2024/2/19 10:33
 */
@Schema(description = "单个单词的详情信息")
public class WordDetailVO {

    @Schema(description = "单词的Id")
    private Long wordId;

    @Schema(description = "单词的元信息表")
    private List<WordDTO> wordDTOList;

    public WordDetailVO() {
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public List<WordDTO> getWordDTOList() {
        return wordDTOList;
    }

    public void setWordDTOList(List<WordDTO> wordDTOList) {
        this.wordDTOList = wordDTOList;
    }
}
