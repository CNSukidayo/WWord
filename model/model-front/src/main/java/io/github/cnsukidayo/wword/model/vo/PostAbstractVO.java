package io.github.cnsukidayo.wword.model.vo;

import io.github.cnsukidayo.wword.model.dto.PostDTO;
import io.github.cnsukidayo.wword.model.dto.UserProfileDTO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2024/2/21 14:24
 */
@Schema(description = "帖子的详情")
public class PostAbstractVO {

    @Schema(description = "帖子的摘要信息")
    private PostDTO postDTO;

    @Schema(description = "帖子创建者的信息")
    private UserProfileDTO postCreateUser;

    public PostAbstractVO() {
    }

    public PostDTO getPostDTO() {
        return postDTO;
    }

    public void setPostDTO(PostDTO postDTO) {
        this.postDTO = postDTO;
    }

    public UserProfileDTO getPostCreateUser() {
        return postCreateUser;
    }

    public void setPostCreateUser(UserProfileDTO postCreateUser) {
        this.postCreateUser = postCreateUser;
    }

}
