package io.github.cnsukidayo.wword.core.controller;

import io.github.cnsukidayo.wword.core.service.PostService;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.param.PublishPostParam;
import io.github.cnsukidayo.wword.model.vo.PostAbstractVO;
import io.github.cnsukidayo.wword.model.vo.PostDetailVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void publishPost(PublishPostParam publishPostParam, User user) {
        postService.publishPost(publishPostParam, user);
    }

    @Operation(summary = "根据帖子的Id获取帖子的详细")
    @GetMapping("uncheck/getPostDetail")
    public PostDetailVo getPostDetailUncheck(@Parameter(description = "帖子的id") @RequestParam("id") Long id) {
        return postService.getPostDetailUncheck(id);
    }

    @Operation(summary = "随机获取帖子列表")
    @GetMapping("uncheck/getPostList")
    public List<PostAbstractVO> getPostListUncheck() {
        return postService.getPostListUncheck();
    }


}
