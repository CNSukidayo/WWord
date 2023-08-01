package io.github.cnsukidayo.wword.model.params;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.Divide;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author sukidayo
 * @date 2023/7/29 13:09
 */
@Schema(description = "添加一个划分的请求体参数")
public class AddDivideParam implements InputConverter<Divide> {

    @Schema(description = "语种id")
    @NotNull(message = "语种id不为null")
    private Long languageId;

    @Schema(description = "名称")
    @NotEmpty(message = "划分名称不为空")
    private String name;

    @Schema(description = "父划分id")
    @NotNull(message = "父划分id不为null")
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
