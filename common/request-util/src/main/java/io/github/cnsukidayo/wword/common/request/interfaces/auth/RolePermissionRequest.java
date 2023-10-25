package io.github.cnsukidayo.wword.common.request.interfaces.auth;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.dto.RoleDTO;
import io.github.cnsukidayo.wword.model.params.PageQueryParam;
import io.github.cnsukidayo.wword.model.params.RoleParam;
import io.github.cnsukidayo.wword.model.params.RolePermissionParam;
import io.github.cnsukidayo.wword.model.params.UserRoleParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;

/**
 * 角色权限管理接口
 *
 * @author sukidayo
 * @date 2023/10/25 16:30
 */
public interface RolePermissionRequest {
    /**
     * 添加一个角色
     *
     * @param roleParam 角色信息参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> addRole(RoleParam roleParam);

    /**
     * 更新一个角色的信息
     *
     * @param roleId    角色id
     * @param roleParam 角色信息参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> updateRole(Long roleId,
                                                   RoleParam roleParam);

    /**
     * 删除一个角色的信息
     *
     * @param roleId 角色id
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> removeRole(Long roleId);

    /**
     * 分页查询所有角色并按照优先级排序
     *
     * @param pageQueryParam 分页查询参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<IPage<RoleDTO>>> rolePage(PageQueryParam pageQueryParam);

    /**
     * 为一个角色分配多个权限接口
     *
     * @param rolePermissionParam 角色权限参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> grantPermission(RolePermissionParam rolePermissionParam);

    /**
     * 删除一个角色的所有接口权限
     *
     * @param roleId 角色id
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> revokePermission(Long roleId);

    /**
     * 分页查询一个角色的所有接口
     *
     * @param roleId         角色id
     * @param pageQueryParam 分页查询参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> rolePermissionPage(Long roleId,
                                                           PageQueryParam pageQueryParam);

    /**
     * 为一个用户分配多个角色
     *
     * @param userRoleParam 用户角色参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> rolePermissionPage(UserRoleParam userRoleParam);

    /**
     * 删除一个用户的所有角色
     *
     * @param uuid 角色id
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> rolePermissionPage(Long uuid);

    /**
     * 分页查询一个用户的所有角色
     *
     * @param uuid           角色id
     * @param pageQueryParam 分页查询参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<IPage<RoleDTO>>> userRolePage(Long uuid,
                                                               PageQueryParam pageQueryParam);

    /**
     * 将当前用户的权限克隆给一个用户
     *
     * @param userRoleParam 用户角色参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> cloneBatch(UserRoleParam userRoleParam);


}
