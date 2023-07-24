package io.github.cnsukidayo.wword.controller.u;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/5/17 20:30
 */
@RestController
@RequestMapping("/api/u/categories")
public class CategoryController {


    @PostMapping("ping")
    public String ping() {
        return "pong";
    }


}
