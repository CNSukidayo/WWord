package io.github.cnsukidayo.wword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("word_id")
    private Long wordId;

    @TableField("word_structure_id")
    private Long wordStructureId;

    @TableField("value")
    private String value;

    @TableField("group_id")
    private Long groupId;

    public Word() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.value = value.replace('；', ';')
                .replace('，', ',')
                .replace('。', '.')
                .replace('：', ':');
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
