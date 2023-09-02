package io.github.cnsukidayo.wword.core.controller;

import io.github.cnsukidayo.wword.core.service.PostCategoryService;
import io.github.cnsukidayo.wword.model.dto.PostCategoryDTO;
import io.github.cnsukidayo.wword.model.params.AddPostCategoryParam;
import io.github.cnsukidayo.wword.model.params.UpdatePostCategoryParam;
import io.github.cnsukidayo.wword.model.entity.PostCategory;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.vo.PostCategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/5/17 20:30
 */
@Tag(name = "用户帖子收藏管理接口")
@RestController
@RequestMapping("/api/u/post_category")
public class PostCategoryController {

    private final PostCategoryService postCategoryService;

    public PostCategoryController(PostCategoryService postCategoryService) {
        this.postCategoryService = postCategoryService;
    }

    @Operation(summary = "添加一个收藏夹")
    @PostMapping("save")
    public void save(@RequestBody @Valid AddPostCategoryParam addPostCategoryParam, User user) {
        postCategoryService.save(addPostCategoryParam, user.getUuid());
    }

    @Operation(summary = "查询当前用户的所有收藏夹")
    @GetMapping("list")
    public List<PostCategoryDTO> list(User user) {
        return postCategoryService.list(user.getUuid())
                .stream()
                .map((Function<PostCategory, PostCategoryDTO>) postCategory -> new PostCategoryVO().convertFrom(postCategory))
                .collect(Collectors.toList());
    }

    @Operation(summary = "根据id查询某个用户所有公开的帖子收藏夹")
    @GetMapping("listPublic")
    public List<PostCategoryDTO> listPublic(@Parameter(description = "目标用户的UUID") @RequestParam("UUID") Long UUID) {
        return postCategoryService.listPublic(UUID)
                .stream()
                .map((Function<PostCategory, PostCategoryDTO>) postCategory -> new PostCategoryVO().convertFrom(postCategory))
                .collect(Collectors.toList());
    }

    @Operation(summary = "更新某个收藏夹的信息")
    @PostMapping("update")
    public void update(@RequestBody UpdatePostCategoryParam updatePostCategoryParam, User user) {
        postCategoryService.updateByUUID(updatePostCategoryParam, user.getUuid());
    }

    @Operation(summary = "删除某个收藏夹&取消收藏某个收藏夹")
    @GetMapping("remove")
    public void remove(@Parameter(description = "收藏夹的id") @RequestParam("id") Long id, User user) {
        postCategoryService.removeById(id, user.getUuid());
    }

    @Operation(summary = "点赞某个收藏夹")
    @GetMapping("like")
    public @ApiResponse(responseCode = "200", description = "返回值为布尔值,true代表成功.false代表失败") Boolean
    like(@Parameter(description = "收藏夹的id") @RequestParam("id") Long id, User user) {
        return postCategoryService.like(id, user.getUuid());
    }

    @Operation(summary = "取消点赞某个收藏夹")
    @GetMapping("dislike")
    public @ApiResponse(responseCode = "200", description = "返回值为布尔值,true代表成功.false代表失败") Boolean
    dislike(@Parameter(description = "收藏夹的id") @RequestParam("id") Long id, User user) {
        return postCategoryService.dislike(id, user.getUuid());
    }

    @Operation(summary = "查询某个收藏夹的详细信息")
    @GetMapping("getById")
    public PostCategoryVO getById(@Parameter(description = "收藏夹的id") @RequestParam("id") Long id, User user) {
        return postCategoryService.getById(id, user.getUuid());
    }

    @Operation(summary = "收藏某个收藏夹")
    @GetMapping("star")
    public void star(@Parameter(description = "收藏夹id") @RequestParam("id") Long id, User user) {
        postCategoryService.star(id, user.getUuid());
    }


}
