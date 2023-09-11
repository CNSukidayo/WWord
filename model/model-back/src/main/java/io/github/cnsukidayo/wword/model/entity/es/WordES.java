package io.github.cnsukidayo.wword.model.entity.es;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Map;

/**
 * 高版本ES不存在type的说法了
 *
 * @author sukidayo
 * @date 2023/9/10 23:03
 */
@Document(indexName = "wword")
public class WordES {

    @Id
    private Long wordId;

    @Field(type = FieldType.Keyword, index = false)
    private String word;

    @Field(type = FieldType.Integer)
    private Long languageId;

    @Field(type = FieldType.Object)
    private Map<Long, String> detail;

    public WordES() {
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public Map<Long, String> getDetail() {
        return detail;
    }

    public void setDetail(Map<Long, String> detail) {
        this.detail = detail;
    }
}
