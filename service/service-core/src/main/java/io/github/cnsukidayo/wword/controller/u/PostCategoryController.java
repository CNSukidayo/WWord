package io.github.cnsukidayo.wword.controller.u;

import io.github.cnsukidayo.wword.model.dto.PostCategoryDTO;
import io.github.cnsukidayo.wword.model.params.AddPostCategoryParam;
import io.github.cnsukidayo.wword.model.params.UpdatePostCategoryParam;
import io.github.cnsukidayo.wword.model.pojo.PostCategory;
import io.github.cnsukidayo.wword.model.pojo.User;
import io.github.cnsukidayo.wword.model.vo.PostCategoryVO;
import io.github.cnsukidayo.wword.service.PostCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        postCategoryService.save(addPostCategoryParam, user.getUUID());
    }

    @Operation(summary = "查询当前用户的所有收藏夹")
    @GetMapping("list")
    public List<PostCategoryDTO> list(User user) {
        return convertPostCategoryDTOList(postCategoryService.list(user.getUUID()));
    }

    @Operation(summary = "根据id查询某个用户所有公开的帖子收藏夹")
    @GetMapping("listPublic")
    public List<PostCategoryDTO> listPublic(@Parameter(description = "目标用户的UUID") @RequestParam("UUID") Long UUID) {
        return convertPostCategoryDTOList(postCategoryService.listPublic(UUID));
    }

    @Operation(summary = "更新某个收藏夹的信息")
    @PostMapping("update")
    public void update(@RequestBody UpdatePostCategoryParam updatePostCategoryParam, User user) {
        postCategoryService.updateByUUID(updatePostCategoryParam, user.getUUID());
    }

    @Operation(summary = "删除某个收藏夹")
    @GetMapping("remove")
    public void remove(@Parameter(description = "收藏夹的id") @RequestParam("id") Long id, User user) {
        postCategoryService.removeById(id, user.getUUID());
    }

    @Operation(summary = "点赞某个收藏夹")
    @GetMapping("like")
    public @ApiResponse(responseCode = "200", description = "返回值为布尔值,true代表成功.false代表失败") Boolean
    like(@Parameter(description = "收藏夹的id") @RequestParam("id") Long id, User user) {
        return postCategoryService.like(id, user.getUUID());
    }

    @Operation(summary = "取消点赞某个收藏夹")
    @GetMapping("dislike")
    public @ApiResponse(responseCode = "200", description = "返回值为布尔值,true代表成功.false代表失败") Boolean
    dislike(@Parameter(description = "收藏夹的id") @RequestParam("id") Long id, User user) {
        return postCategoryService.dislike(id, user.getUUID());
    }

    @Operation(summary = "查询某个收藏夹的详细信息")
    @GetMapping("getById")
    public PostCategoryVO getById(@Parameter(description = "收藏夹的id") @RequestParam("id") Long id, User user) {
        PostCategory postCategory = postCategoryService.getById(id, user.getUUID());
        PostCategoryVO postCategoryVO = postCategory.convertToDTO(new PostCategoryVO());
        postCategoryVO.setLikeCount(postCategoryService.likeCount(id));
        return postCategoryVO;
    }


    /**
     * 将PostCategory集合转换为PostCategoryDTO集合
     *
     * @param postCategoryList 参数不为null
     * @return 返回PostCategoryDTO集合
     */
    private List<PostCategoryDTO> convertPostCategoryDTOList(List<PostCategory> postCategoryList) {
        Assert.notNull(postCategoryList, "postCategoryList must not be null");
        List<PostCategoryDTO> postCategoryDTOList = new ArrayList<>(postCategoryList.size());
        postCategoryList.forEach(postCategory -> postCategoryDTOList.add(postCategory.convertToDTO(new PostCategoryDTO())));
        return postCategoryDTOList;
    }

}
