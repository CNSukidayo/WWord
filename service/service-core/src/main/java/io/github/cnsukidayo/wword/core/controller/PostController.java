package io.github.cnsukidayo.wword.core.controller;

import io.github.cnsukidayo.wword.core.service.PostService;
import io.github.cnsukidayo.wword.model.param.PublishPostParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/9/12 18:09
 */
@Tag(name = "帖子管理接口")
@RestController
@RequestMapping("/api/u/post")
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
