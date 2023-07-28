package io.github.cnsukidayo.wword.model.dto;

import io.github.cnsukidayo.wword.model.enums.CategoryAttribute;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/27 10:10
 */
@Schema(description = "用户帖子收藏夹")
public class PostCategoryDTO {

    @Schema(description = "当前收藏夹的id")
    private Long id;

    @Schema(description = "当前收藏夹隶属于哪个用户")
    private Long UUID;

    @Schema(description = "收藏夹封面URL")
    private String coverImage;

    @Schema(description = "收藏夹名称")
    private String name;

    @Schema(description = "收藏夹描述信息")
    private String describeInfo;

    @Schema(description = "收藏夹的公开类型", defaultValue = "PRIVATE")
    private CategoryAttribute categoryAttribute;

    @Schema(description = "创建者名称")
    private String createName;

    public PostCategoryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUUID() {
        return UUID;
    }

    public void setUUID(Long UUID) {
        this.UUID = UUID;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    public void setDescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo;
    }

    public CategoryAttribute getCategoryAttribute() {
        return categoryAttribute;
    }

    public void setCategoryAttribute(CategoryAttribute categoryAttribute) {
        this.categoryAttribute = categoryAttribute;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
