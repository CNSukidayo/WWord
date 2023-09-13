package io.github.cnsukidayo.wword.auth.client;

import io.github.cnsukidayo.wword.model.bo.UserPermissionBO;
import io.github.cnsukidayo.wword.model.params.CheckAuthParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

/**
 * @author sukidayo
 * @date 2023/9/7 19:34
 */
@ReactiveFeignClient("service-auth")
public interface AuthReactiveFeignClient {

    /**
     * 得到并且校验目标用户的权限<br>
     * 关于返回值,由于执行的过程中可能出现异常,所以{@link BaseResponse}中包装的data不一定是{@link UserPermissionBO}对象
     * 也有可能是{@link ErrorVo} 对象,所以gateway网关需要通过BaseResponse返回的状态码来判断本次返回的data类型是什么.<br>
     * 如果返回码是200则代表获取用户成功(但并不代表该用户有当前接口的权限,需要通过{@link UserPermissionBO#isHasPermission()}
     * 方法的返回值来判断.<br>
     * 如果返回是非200码则代表出现了异常,根据约定会返回{@link ErrorVo}
     *
     * @param checkAuthParam 权限校验参数不为null
     * @return 返回值不为null
     */
    @PostMapping("/remote/react/auth/permission/get_and_check")
    Mono<BaseResponse<Object>> getAndCheck(@RequestBody CheckAuthParam checkAuthParam);

}
