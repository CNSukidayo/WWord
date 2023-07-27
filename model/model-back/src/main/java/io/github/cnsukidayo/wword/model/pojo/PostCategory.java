package io.github.cnsukidayo.wword.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.dto.PostCategoryDTO;
import io.github.cnsukidayo.wword.model.enums.PublicNess;
import io.github.cnsukidayo.wword.model.pojo.base.BaseEntity;

import java.io.Serial;

/**
 * @author sukidayo
 * @date 2023/7/26 20:25
 */
@TableName("post_category")
public class PostCategory extends BaseEntity<PostCategoryDTO> {
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

    @TableField("public_ness")
    private PublicNess publicNess;

    @TableField("like_count")
    private Integer likeCount;

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

    public PublicNess getPublicNess() {
        return publicNess;
    }

    public void setPublicNess(PublicNess publicNess) {
        this.publicNess = publicNess;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

}
