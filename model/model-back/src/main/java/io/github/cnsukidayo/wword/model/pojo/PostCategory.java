package io.github.cnsukidayo.wword.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.enums.CategoryAttribute;
import io.github.cnsukidayo.wword.model.pojo.base.BaseEntity;

import java.io.Serial;

/**
 * @author sukidayo
 * @date 2023/7/26 20:25
 */
@TableName("post_category")
public class PostCategory extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("uuid")
    private Long UUID;

    @TableField("cover_image")
    private String coverImage;

    @TableField("name")
    private String name;

    @TableField("describe_info")
    private String describeInfo;

    @TableField("category_attribute")
    private CategoryAttribute categoryAttribute;

    @TableField(exist = false)
    private String createName;

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
