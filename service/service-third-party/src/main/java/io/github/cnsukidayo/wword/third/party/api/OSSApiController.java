package io.github.cnsukidayo.wword.third.party.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/9/12 14:14
 */
@Tag(name = "对象存储服务接口")
@RestController
@RequestMapping("remote/third/party/oss")
public class OSSApiController {

    @Operation(summary = "")
    public void fileUpLoad() {

    }

}
