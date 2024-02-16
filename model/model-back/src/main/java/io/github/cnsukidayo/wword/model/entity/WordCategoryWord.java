package io.github.cnsukidayo.wword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.entity.base.BaseEntityNoLogic;

/**
 * @author sukidayo
 * @date 2023/7/26 20:25
 */
@TableName("word_category_word")
public class WordCategoryWord extends BaseEntityNoLogic {

    @TableId(value = "word_category_id", type = IdType.INPUT)
    private Long wordCategoryId;

    @TableField("word_id")
    private Long wordId;

    @TableField("word_order")
    private Integer wordOrder;

    public WordCategoryWord() {
    }

    public Long getWordCategoryId() {
        return wordCategoryId;
    }

    public void setWordCategoryId(Long wordCategoryId) {
        this.wordCategoryId = wordCategoryId;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public Integer getWordOrder() {
        return wordOrder;
    }

    public void setWordOrder(Integer wordOrder) {
        this.wordOrder = wordOrder;
    }
}