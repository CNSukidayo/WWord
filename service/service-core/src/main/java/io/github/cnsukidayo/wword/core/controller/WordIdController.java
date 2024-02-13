package io.github.cnsukidayo.wword.core.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/10/31 16:08
 */
@Tag(name = "wordID管理接口(用于存放所有父划分下的单词信息)")
@RestController
@RequestMapping("/api/word/wordId")
public class WordIdController {
    // todo 参照WordIdApiController类

}
