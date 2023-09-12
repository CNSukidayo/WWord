package io.github.cnsukidayo.wword.third.oss.api;

import io.github.cnsukidayo.wword.third.oss.service.OSSService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sukidayo
 * @date 2023/9/12 14:14
 */
@Tag(name = "对象存储服务接口")
@RestController
@RequestMapping("/remote/third/party/oss")
public class OSSApiController {

    private final OSSService ossService;

    public OSSApiController(OSSService ossService) {
        this.ossService = ossService;
    }

    @Operation(summary = "上传文件到阿里云")
    @PostMapping("fileUpLoad")
    public String fileUpLoad(@RequestParam("file") MultipartFile multipartFile) {
        return ossService.fileUpLoad(multipartFile);
    }

}
