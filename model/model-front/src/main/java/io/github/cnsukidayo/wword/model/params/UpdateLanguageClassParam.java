package io.github.cnsukidayo.wword.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * @author sukidayo
 * @date 2023/7/29 20:12
 */
@Schema(description = "更新语种信息")
public class UpdateLanguageClassParam extends AddLanguageClassParam {

    @Schema(description = "语种id")
    @NotNull(message = "语种id不为null")
    private Long id;

    public UpdateLanguageClassParam() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
