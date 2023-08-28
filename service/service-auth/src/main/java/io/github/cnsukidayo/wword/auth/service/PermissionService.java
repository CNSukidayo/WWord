package io.github.cnsukidayo.wword.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.Permission;
import io.github.cnsukidayo.wword.model.enums.PermissionStatus;
import io.github.cnsukidayo.wword.model.vo.PermissionVO;

import java.util.List;
import java.util.Map;

/**
 * @author sukidayo
 * @date 2023/8/27 21:47
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 分组取得所有跟踪的接口,该方法会把结果进行封装.<br>
     * 如果跟踪的接口已经失效则{@link PermissionVO#getPermissionStatus()}的返回值为{@link PermissionStatus#FAIL}<br>
     * 如果接口正常则返回{@link PermissionStatus#SUCCESS}
     *
     * @return 返回值不为null
     */
    Map<String, Map<String, List<PermissionVO>>> getTraceByGroup();

    /**
     * 得到所有的权限接口
     *
     * @return 返回值可能为null
     */
    List<Permission> getTraces();


}
