package io.github.cnsukidayo.wword.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author sukidayo
 * @date 2023/7/31 10:00
 */
@TableName("enwords")
public class EnWords {

    @TableId(value = "word")
    private String word;

    @TableField("translation")
    private String translation;

    public EnWords() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
