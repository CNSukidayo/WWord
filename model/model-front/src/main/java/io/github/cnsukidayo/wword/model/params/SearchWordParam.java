package io.github.cnsukidayo.wword.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author sukidayo
 * @date 2023/9/11 19:37
 */
@Schema(description = "分页搜索单词的查询参数")
public class SearchWordParam extends PageQueryParam {

    @Schema(description = "要查询的单词")
    @NotEmpty(message = "搜索的内容不为空")
    private String word;

    @Schema(description = "词库的id")
    @NotNull(message = "语种ID不能为null")
    private Long languageId;

    public SearchWordParam() {
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
}
