package io.github.cnsukidayo.wword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.enums.DivideType;
import io.github.cnsukidayo.wword.model.entity.base.BaseEntity;

/**
 * @author sukidayo
 * @date 2023/7/28 16:06
 */
@TableName("divide")
public class Divide extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("uuid")
    private Long UUID;

    @TableField("language_id")
    private Long languageId;

    @TableField("name")
    private String name;

    @TableField("parent_id")
    private Long parentId;

    @TableField("divide_type")
    private DivideType divideType;

    public Divide() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUUID() {
        return UUID;
    }

    public void setUUID(Long UUID) {
        this.UUID = UUID;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public DivideType getDivideType() {
        return divideType;
    }

    public void setDivideType(DivideType divideType) {
        this.divideType = divideType;
    }
}
