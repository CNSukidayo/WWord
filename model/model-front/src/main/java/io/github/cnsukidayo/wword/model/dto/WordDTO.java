package io.github.cnsukidayo.wword.model.dto;

import io.github.cnsukidayo.wword.model.base.OutputConverter;
import io.github.cnsukidayo.wword.model.entity.Word;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/28 16:30
 */
@Schema(description = "单词元信息")
public class WordDTO implements OutputConverter<WordDTO, Word> {
    @Schema(description = "单词组标记")
    private Long groupFlag;

    @Schema(description = "单词的id")
    private Long wordId;

    @Schema(description = "单词对应的结构体id")
    private Long wordStructureId;

    @Schema(description = "单词对应的值")
    private String value;

    @Schema(description = "单词组id")
    private Long groupId;

    public WordDTO() {
    }

    public Long getGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(Long groupFlag) {
        this.groupFlag = groupFlag;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public Long getWordStructureId() {
        return wordStructureId;
    }

    public void setWordStructureId(Long wordStructureId) {
        this.wordStructureId = wordStructureId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
