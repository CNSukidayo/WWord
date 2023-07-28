package io.github.cnsukidayo.wword.model.params;

import io.github.cnsukidayo.wword.model.enums.CategoryAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author sukidayo
 * @date 2023/7/27 9:45
 */
@Schema(description = "添加收藏夹请求体参数")
public class AddPostCategoryParam {

    @Schema(description = "收藏夹封面URL")
    @Size(max = 255, message = "收藏夹封面地址长度不超过 {max}")
    private String coverImage;

    @Schema(description = "收藏夹名称")
    @NotBlank(message = "收藏夹名称不能为空")
    @Size(max = 16, message = "收藏夹名称长度不超过 {max}")
    private String name;

    @Schema(description = "收藏夹描述信息")
    @Size(max = 255, message = "收藏夹描述信息长度不超过{max}")
    private String describeInfo;

    @Schema(description = "收藏夹的公开类型", defaultValue = "PRIVATE")
    private CategoryAttribute categoryAttribute;
    
    public AddPostCategoryParam() {
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
}
