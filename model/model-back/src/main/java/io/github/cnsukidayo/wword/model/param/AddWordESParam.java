package io.github.cnsukidayo.wword.model.param;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.es.WordES;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

/**
 * @author sukidayo
 * @date 2023/9/10 22:58
 */
@Schema(description = "添加单词到ES的请求参数")
public class AddWordESParam implements InputConverter<WordES> {

    @Schema(description = "单词Id")
    @NotNull(message = "单词id不为null")
    private Long wordId;

    @Schema(description = "单词原文(origin)")
    @NotBlank(message = "单词不能为空")
    private String word;

    @Schema(description = "语种Id")
    @NotNull(message = "语种id不为null")
    private Long languageId;

    @Schema(description = "key:是单词的structure_id value:该结构的字段信息")
    @NotEmpty(message = "单词详细信息不能为空")
    private Map<Long, String> detail;

    public AddWordESParam() {
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
