package io.github.cnsukidayo.wword.model.params;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/29 13:09
 */
@Schema(description = "添加一个划分的请求体参数")
public class AddDivideParam {

    @Schema(description = "语种id")
    private Long languageId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "父划分id")
    private Long parentId;


    public AddDivideParam() {
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
}
