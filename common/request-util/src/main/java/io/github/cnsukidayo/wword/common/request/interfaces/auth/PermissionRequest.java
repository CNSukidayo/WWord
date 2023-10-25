package io.github.cnsukidayo.wword.common.request.interfaces.auth;

import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.params.PermissionParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.PermissionVO;

import java.util.List;
import java.util.Map;

/**
 * 权限管理接口
 *
 * @author sukidayo
 * @date 2023/10/25 15:51
 */
public interface PermissionRequest {

    /**
     * 跟踪一个接口
     *
     * @param permissionParam 跟踪的权限参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> trace(PermissionParam permissionParam);

    /**
     * 分组取得所有跟踪的接口
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Map<String, Map<String, List<PermissionVO>>>>> getTraceByGroup();

    /**
     * 批量撤销接口的跟踪
     *
     * @param permissionIdList 待取消跟踪的接口的id
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> untrace(List<Long> permissionIdList);

    /**
     * 更新一个跟踪接口的信息
     *
     * @param permissionId    跟踪的目标接口id
     * @param permissionParam 权限接口参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> updateTrace(String permissionId,
                                                    PermissionParam permissionParam);


}
