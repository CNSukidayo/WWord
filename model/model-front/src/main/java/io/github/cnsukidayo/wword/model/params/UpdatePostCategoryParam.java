package io.github.cnsukidayo.wword.model.params;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/27 13:26
 */
@Schema(description = "用户更新收藏夹参数")
public class UpdatePostCategoryParam extends AddPostCategoryParam {

    @Schema(description = "收藏夹的id")
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
