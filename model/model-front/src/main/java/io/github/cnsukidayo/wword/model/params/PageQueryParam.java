package io.github.cnsukidayo.wword.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author sukidayo
 * @date 2023/8/27 21:57
 */
@Schema(description = "分页查询参数")
public class PageQueryParam {
    @Schema(description = "当前是第几页")
    @NotNull(message = "当前页必须不为null")
    private Integer current;

    @Schema(description = "每页的数据数量")
    @NotNull(message = "每页数据数必须不为null")
    @Size(max = 50, message = "每页数量不能超过{max}")
    private Integer size;

    public PageQueryParam() {
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
