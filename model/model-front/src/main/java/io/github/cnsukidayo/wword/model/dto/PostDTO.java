package io.github.cnsukidayo.wword.model.dto;

import io.github.cnsukidayo.wword.model.base.OutputConverter;
import io.github.cnsukidayo.wword.model.entity.Post;
import io.github.cnsukidayo.wword.model.enums.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/8/28 22:15
 */
@Schema(description = "帖子的摘要信息")
public class PostDTO implements OutputConverter<PostDTO, Post> {

    @Schema(description = "帖子的id")
    private Long id;

    @Schema(description = "帖子的标题信息")
    private String title;

    @Schema(description = "帖子的创建人")
    private Long uuid;

    @Schema(description = "帖子发布的状态")
    private PostStatus postStatus;

    @Schema(description = "帖子的描述信息,该信息由系统创建")
    private String describeInfo;

    public PostDTO() {
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

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    public void setDescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }
}
