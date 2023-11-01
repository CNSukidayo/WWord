package io.github.cnsukidayo.wword.model.dto;

import io.github.cnsukidayo.wword.model.base.OutputConverter;
import io.github.cnsukidayo.wword.model.entity.DivideWord;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/29 17:02
 */
@Schema(description = "划分下的单词")
public class DivideWordDTO implements OutputConverter<DivideWordDTO, DivideWord> {

    @Schema(description = "子划分的id")
    private Long divideId;

    @Schema(description = "单词的id")
    private Long wordId;

    @Schema(description = "单词的名称")
    private String wordName;

    @Schema(description = "哪个用户收藏的该单词")
    private Long uuid;

    @Schema(description = "该单词在当前划分中的顺序,主要用于单词的排序展示")
    private Integer order;

    public DivideWordDTO() {
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
