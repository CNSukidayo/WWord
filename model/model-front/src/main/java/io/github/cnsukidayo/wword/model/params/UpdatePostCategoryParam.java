package io.github.cnsukidayo.wword.model.params;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.pojo.PostCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * @author sukidayo
 * @date 2023/7/27 13:26
 */
@Schema(description = "用户更新收藏夹参数")
public class UpdatePostCategoryParam extends AddPostCategoryParam implements InputConverter<PostCategory> {

    @Schema(description = "收藏夹的id")
    @NotNull(message = "收藏夹id不为null")
    private Long id;

    public UpdatePostCategoryParam() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
