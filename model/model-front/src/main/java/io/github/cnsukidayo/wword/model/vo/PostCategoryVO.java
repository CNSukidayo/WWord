package io.github.cnsukidayo.wword.model.vo;

import io.github.cnsukidayo.wword.model.dto.PostCategoryDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/27 18:21
 */
@Schema(description = "用户帖子收藏夹详细信息")
public class PostCategoryVO extends PostCategoryDTO  {

    @Schema(description = "当前收藏夹的点赞数")
    private Long likeCount;

    @Schema(description = "当前收藏夹的被收藏数")
    private Long starCount;

    @Schema(description = "发起查询的用户是否已经点赞了")
    private Boolean liked;

    @Schema(description = "发起查询的用户是否已经收藏了")
    private Boolean star;

    @Schema(description = "收藏的帖子列表")
    // todo 待做
    private List<Object> postList;


    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getStarCount() {
        return starCount;
    }

    public void setStarCount(Long starCount) {
        this.starCount = starCount;
    }

    public List<Object> getPostList() {
        return postList;
    }

    public void setPostList(List<Object> postList) {
        this.postList = postList;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Boolean getStar() {
        return star;
    }

    public void setStar(Boolean star) {
        this.star = star;
    }
}
