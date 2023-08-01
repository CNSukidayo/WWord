package io.github.cnsukidayo.wword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.entity.base.BaseEntity;

/**
 * @author sukidayo
 * @date 2023/7/30 14:25
 */
@TableName("word_structure")
public class WordStructure extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("language_id")
    private Long languageId;

    @TableField("field")
    private String field;

    @TableField("field_translation")
    private String fieldTranslation;

    public WordStructure() {
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
