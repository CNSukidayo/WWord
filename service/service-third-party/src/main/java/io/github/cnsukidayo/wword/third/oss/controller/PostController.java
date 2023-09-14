package io.github.cnsukidayo.wword.third.oss.controller;

import io.github.cnsukidayo.wword.model.param.PublishPostParam;
import io.github.cnsukidayo.wword.third.oss.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/9/14 9:41
 */
@Tag(name = "帖子管理接口")
@RestController
@RequestMapping("/api/third/party/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "发布文章")
    @PostMapping("publishPost")
    public void publishPost(PublishPostParam publishPostParam) {
        postService.publishPost(publishPostParam);
    }


}
