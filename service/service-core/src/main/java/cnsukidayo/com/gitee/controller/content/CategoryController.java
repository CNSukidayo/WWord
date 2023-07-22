package cnsukidayo.com.gitee.controller.content;

import io.github.cnsukidayo.wword.support.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/5/17 20:30
 */
@RestController("ApiContentCategoryController")
@RequestMapping("/api/content/categories")
public class CategoryController {

    @GetMapping("/hello")
    public BaseResponse<String> hello() {
        return BaseResponse.ok("good!");
    }

}
