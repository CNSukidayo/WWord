package io.github.cnsukidayo.wword.model.params;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.Divide;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/29 13:09
 */
@Schema(description = "将一个父划分中的单词复制到一个父划分中的参数")
public class WordIdFromOtherParam implements InputConverter<Divide> {

    @Schema(description = "源父划分id")
    @NotNull(message = "源父划分id不为null")
    private Long originDivideId;

    @Schema(description = "单词ids")
    @NotEmpty(message = "单词id列表不为空")
    private List<Long> ids;

    @Schema(description = "目标父划分id")
    @NotNull(message = "目标父划分id不为null")
    private Long targetDivideId;


    public WordIdFromOtherParam() {
    }

    public Long getOriginDivideId() {
        return originDivideId;
    }

    public void setOriginDivideId(Long originDivideId) {
        this.originDivideId = originDivideId;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getTargetDivideId() {
        return targetDivideId;
    }

    public void setTargetDivideId(Long targetDivideId) {
        this.targetDivideId = targetDivideId;
    }
}
