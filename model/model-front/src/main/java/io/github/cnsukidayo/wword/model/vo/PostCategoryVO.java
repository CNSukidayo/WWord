package io.github.cnsukidayo.wword.model.vo;

import io.github.cnsukidayo.wword.model.dto.PostCategoryDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/27 18:21
 */
@Schema(description = "用户帖子收藏夹详细信息")
public class PostCategoryVO extends PostCategoryDTO {

    @Schema(description = "当前收藏夹的点赞数")
    private Integer likeCount;

    @Schema(description = "收藏的帖子列表")
    // todo 待做
    private List<Object> postList;

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public List<Object> getPostList() {
        return postList;
    }

    public void setPostList(List<Object> postList) {
        this.postList = postList;
    }
}
