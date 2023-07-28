package io.github.cnsukidayo.wword.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.dto.WordDivideDTO;
import io.github.cnsukidayo.wword.model.enums.DivideType;
import io.github.cnsukidayo.wword.model.pojo.base.BaseEntity;

/**
 * @author sukidayo
 * @date 2023/7/28 16:06
 */
@TableName("word_divide")
public class WordDivide extends BaseEntity<WordDivideDTO> {

    @TableField("uuid")
    private Long uuid;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("element_count")
    private Long elementCount;

    @TableField("divide_type")
    private DivideType divideType;

    @TableField("child_id")
    private Long childId;

    public WordDivide() {
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getElementCount() {
        return elementCount;
    }

    public void setElementCount(Long elementCount) {
        this.elementCount = elementCount;
    }

    public DivideType getDivideType() {
        return divideType;
    }

    public void setDivideType(DivideType divideType) {
        this.divideType = divideType;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }
}
