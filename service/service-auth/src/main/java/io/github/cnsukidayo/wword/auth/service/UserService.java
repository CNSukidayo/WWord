package io.github.cnsukidayo.wword.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.bo.UserPermissionBO;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.params.CheckAuthParam;

/**
 * @author sukidayo
 * @date 2023/9/7 18:37
 */
public interface UserService extends IService<User> {

    /**
     * 检查一个用户是否当前请求的目标接口的权限,将目标用户和权限结果封装到<br>
     * {@link UserPermissionBO}对象中
     *
     * @param checkAuthParam 校验权限参数不为null
     * @return 返回值不为null
     */
    UserPermissionBO getAndAuth(CheckAuthParam checkAuthParam);

}
