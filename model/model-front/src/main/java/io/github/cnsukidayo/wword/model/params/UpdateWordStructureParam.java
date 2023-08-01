package io.github.cnsukidayo.wword.model.params;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.WordStructure;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author sukidayo
 * @date 2023/7/30 19:35
 */

@Schema(description = "添加单词结构参数")
public class UpdateWordStructureParam implements InputConverter<WordStructure> {

    @Schema(description = "单词结构id;可以为null,为null代表新增字段")
    private Long id;

    @Schema(description = "语种id")
    @NotNull
    private Long languageId;

    @Schema(description = "字段名称")
    @NotEmpty
    private String field;

    @Schema(description = "字段名称的翻译(中文)")
    @NotEmpty
    private String fieldTranslation;

    public UpdateWordStructureParam() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldTranslation() {
        return fieldTranslation;
    }

    public void setFieldTranslation(String fieldTranslation) {
        this.fieldTranslation = fieldTranslation;
    }
}
