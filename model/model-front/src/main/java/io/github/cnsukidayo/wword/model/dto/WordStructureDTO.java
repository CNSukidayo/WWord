package io.github.cnsukidayo.wword.model.dto;

import io.github.cnsukidayo.wword.model.base.OutputConverter;
import io.github.cnsukidayo.wword.model.pojo.WordStructure;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/30 14:36
 */
@Schema(description = "单词结构体")
public class WordStructureDTO implements OutputConverter<WordStructureDTO, WordStructure> {

    @Schema(description = "结构的id")
    private Long id;

    @Schema(description = "语种id")
    private Long languageId;

    @Schema(description = "结构的属性名称")
    private String field;

    @Schema(description = "结构属性名称的翻译(中文)")
    private String fieldTranslation;


    public WordStructureDTO() {
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
