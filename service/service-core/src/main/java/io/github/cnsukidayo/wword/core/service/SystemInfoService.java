package io.github.cnsukidayo.wword.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.SystemInfo;
import io.github.cnsukidayo.wword.model.enums.SystemInfoType;

/**
 * 系统信息接口
 *
 * @author sukidayo
 * @date 2024/2/8 19:37
 */
public interface SystemInfoService extends IService<SystemInfo> {
    /**
     * 获取系统信息
     *
     * @param systemInfoType 传入当前要获取的系统信息类型
     * @return 返回值为对应的系统信息
     */
    String getSystemInfoContext(SystemInfoType systemInfoType);
}
