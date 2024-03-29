package io.github.cnsukidayo.wword.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.entity.base.BaseEntity;

/**
 * @author sukidayo
 * @date 2023/7/29 14:24
 */
@TableName("divide_word")
public class DivideWord extends BaseEntity {

    @TableId("divide_id")
    private Long divideId;

    @TableField("word_id")
    private Long wordId;

    @TableField("word_name")
    private String wordName;

    @TableField("uuid")
    private Long uuid;

    @TableField("divide_order")
    private Integer divideOrder;

    public DivideWord() {
    }

    public Long getDivideId() {
        return divideId;
    }

    public void setDivideId(Long divideId) {
        this.divideId = divideId;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Integer getDivideOrder() {
        return divideOrder;
    }

    public void setDivideOrder(Integer divideOrder) {
        this.divideOrder = divideOrder;
    }
}
