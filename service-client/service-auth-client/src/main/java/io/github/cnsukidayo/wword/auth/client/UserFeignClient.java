package io.github.cnsukidayo.wword.auth.client;

import io.github.cnsukidayo.wword.model.params.CheckAuthParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author sukidayo
 * @date 2023/9/7 19:34
 */
@FeignClient("service-auth")
public interface UserFeignClient {

    @PostMapping("remote/auth/permission/getAndCheck")
    BaseResponse<Object> getAndCheck(CheckAuthParam checkAuthParam);



}
