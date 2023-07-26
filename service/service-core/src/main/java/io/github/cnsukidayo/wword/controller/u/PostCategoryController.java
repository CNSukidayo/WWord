package io.github.cnsukidayo.wword.controller.u;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/5/17 20:30
 */
@Tag(name = "用户帖子收藏管理接口")
@RestController
@RequestMapping("/api/u/post_category")
public class PostCategoryController {



    @Operation(summary = "添加一个收藏夹")
    @PostMapping("save")
    public void save() {

    }


}
