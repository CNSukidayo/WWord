package io.github.cnsukidayo.wword.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/7/28 18:17
 */
@RestController
public class Hello {

    @RequestMapping("ping")
    public String ping() {
        return "pong";
    }

}
