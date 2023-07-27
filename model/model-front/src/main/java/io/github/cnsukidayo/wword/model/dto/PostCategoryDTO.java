package io.github.cnsukidayo.wword.model.dto;

import io.github.cnsukidayo.wword.model.enums.PublicNess;
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
    private PublicNess publicNess;

    @Schema(description = "当前收藏夹的点赞数")
    private Integer likeCount;

    @Schema(description = "发起查询的用户是否已经点赞了")
    private Boolean liked;

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

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "PostCategoryDTO{" +
                "id=" + id +
                ", uuid=" + UUID +
                ", coverImage='" + coverImage + '\'' +
                ", name='" + name + '\'' +
                ", describeInfo='" + describeInfo + '\'' +
                ", publicNess=" + publicNess +
                ", likeCount=" + likeCount +
                ", liked=" + liked +
                '}';
    }
}
