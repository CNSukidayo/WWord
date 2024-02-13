package io.github.cnsukidayo.wword.model.dto.es;

import io.github.cnsukidayo.wword.model.base.OutputConverter;
import io.github.cnsukidayo.wword.model.entity.es.WordES;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

/**
 * @author sukidayo
 * @date 2024/2/12 20:02
 */
@Schema(description = "单词的简要信息,单词在ES中的信息")
public class WordESDTO implements OutputConverter<WordESDTO, WordES> {

    @Schema(description = "单词的id")
    private Long wordId;

    @Schema(description = "单词原文")
    private String word;

    @Schema(description = "语种Id")
    private Long languageId;

    @Schema(description = "单词的信息")
    private Map<Long, String> detail;

    public WordESDTO() {
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public Map<Long, String> getDetail() {
        return detail;
    }

    public void setDetail(Map<Long, String> detail) {
        this.detail = detail;
    }
}
