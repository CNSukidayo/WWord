package cnsukidayo.com.gitee.controller.content;

import cnsukidayo.com.gitee.model.support.BaseResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    private StringRedisTemplate stringRedisTemplate;

    public CategoryController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @GetMapping("/redis")
    public BaseResponse<Long> redis() {
        Long id = Long.valueOf(stringRedisTemplate.opsForValue().get("token"));
        return BaseResponse.ok(id);
    }

}
