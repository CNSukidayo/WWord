package io.github.cnsukidayo.wword.model.dto;

import io.github.cnsukidayo.wword.model.enums.DivideType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/28 16:30
 */
@Schema(description = "单词划分的树状层次展示")
public class DivideDTO {

    @Schema(description = "划分的id")
    private Long id;

    @Schema(description = "语种id")
    private Long languageId;

    @Schema(description = "划分的名称")
    private String name;

    @Schema(description = "word_id")
    private Long wordId;

    @Schema(description = "子划分个数")
    private Long elementCount;

    @Schema(description = "当前划分的类型")
    private DivideType divideType;

    @Schema(description = "子划分集合")
    private List<DivideDTO> childDivideDTO;

    public DivideDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
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

    public List<DivideDTO> getChildDivideDTO() {
        return childDivideDTO;
    }

    public void setChildDivideDTO(List<DivideDTO> childDivideDTO) {
        this.childDivideDTO = childDivideDTO;
    }
}
