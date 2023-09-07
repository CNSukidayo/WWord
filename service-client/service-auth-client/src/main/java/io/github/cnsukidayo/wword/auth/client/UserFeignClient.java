package io.github.cnsukidayo.wword.auth.client;

import io.github.cnsukidayo.wword.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

/**
 * @author sukidayo
 * @date 2023/9/7 19:34
 */
@FeignClient("service-auth")
public interface UserFeignClient {

    @GetMapping("remote/auth/permission/getById/{uuid}")
    User getById(@PathVariable("uuid") Serializable uuid);



}
