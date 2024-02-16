package io.github.cnsukidayo.wword.model.dto;

import io.github.cnsukidayo.wword.model.base.OutputConverter;
import io.github.cnsukidayo.wword.model.entity.WordCategory;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/27 10:10
 */
@Schema(description = "用户帖子收藏夹")
public class WordCategoryDTO implements OutputConverter<WordCategoryDTO, WordCategory> {

    @Schema(description = "当前收藏夹的id")
    private Long id;

    @Schema(description = "收藏夹名称")
    private String title;

    @Schema(description = "收藏夹描述信息")
    private String describeInfo;

    @Schema(description = "收藏夹的排序")
    private Integer categoryOrder;

    public WordCategoryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    public void setDescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo;
    }

    public Integer getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(Integer categoryOrder) {
        this.categoryOrder = categoryOrder;
    }
}
