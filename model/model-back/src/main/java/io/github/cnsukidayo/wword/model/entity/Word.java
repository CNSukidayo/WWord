package io.github.cnsukidayo.wword.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.entity.base.BaseEntity;

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

    @TableField("divide_id")
    private Long divideId;

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

    public Long getDivideId() {
        return divideId;
    }

    public void setDivideId(Long divideId) {
        this.divideId = divideId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
