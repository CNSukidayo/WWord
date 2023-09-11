package io.github.cnsukidayo.wword.admin.controller;

import io.github.cnsukidayo.wword.admin.service.WordHandleService;
import io.github.cnsukidayo.wword.model.params.AddOrUpdateWordParam;
import io.github.cnsukidayo.wword.model.params.UpLoadWordJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author sukidayo
 * @date 2023/7/30 21:44
 */
@Tag(name = "单词处理接口")
@RestController
@RequestMapping("/api/admin/word_handle")
public class WordHandleController {

    private final WordHandleService wordHandleService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public WordHandleController(WordHandleService wordHandleService) {
        this.wordHandleService = wordHandleService;
    }

    @Operation(summary = "手动添加一个单词")
    @PostMapping("saveWord")
    public void saveWord(@RequestBody @Valid AddOrUpdateWordParam addOrUpdateWordParam) {
        wordHandleService.saveWord(addOrUpdateWordParam);
    }

    @Operation(summary = "处理json丰富信息")
    @PostMapping("handleJson")
    public void handleJson(@Param("上传单词Json文件") @Valid UpLoadWordJson upLoadWordJson) {
        String fileName = upLoadWordJson.getFile().getOriginalFilename();
        log.info("receive request file{},await Async handler.", fileName);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            IOUtils.copy(upLoadWordJson.getFile().getInputStream(), byteArrayOutputStream);
        } catch (IOException e) {
            log.error("copy file [{}] fail", fileName);
            throw new RuntimeException(e);
        }
        wordHandleService.handleJson(upLoadWordJson, new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }

    @Operation(summary = "更新单词总库")
    @GetMapping("updateBase")
    public void updateBase(@RequestParam("divideId") Long divideId) {
        // todo 可以添加事件
        log.info("updateBase start");
        wordHandleService.updateBase(divideId);
    }

    @Operation(summary = "发布基本库的单词到ES中")
    @GetMapping("updateESBase")
    public void updateESBase(@Parameter(description = "基本库Id") @RequestParam("baseId") Long baseId,
                             @Parameter(description = "语种id") @RequestParam("languageId") Long languageId) {
        wordHandleService.updateESBase(baseId, languageId);
    }

}
