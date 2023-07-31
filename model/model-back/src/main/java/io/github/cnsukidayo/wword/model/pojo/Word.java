package io.github.cnsukidayo.wword.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.pojo.base.BaseEntity;

/**
 * @author sukidayo
 * @date 2023/7/31 13:14
 */
@TableName("word")
public class Word extends BaseEntity {

    @TableId(value = "id")
    private Long id;

    @TableField("word_structure_id")
    private Long wordStructureId;

    @TableField("language_id")
    private Long languageId;

    @TableField("value")
    private String value;

    public Word() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWordStructureId() {
        return wordStructureId;
    }

    public void setWordStructureId(Long wordStructureId) {
        this.wordStructureId = wordStructureId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
