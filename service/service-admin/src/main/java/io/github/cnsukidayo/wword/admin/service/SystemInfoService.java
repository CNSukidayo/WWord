package io.github.cnsukidayo.wword.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.dto.SystemInfoDTO;
import io.github.cnsukidayo.wword.model.entity.SystemInfo;
import io.github.cnsukidayo.wword.model.params.PageQueryParam;
import io.github.cnsukidayo.wword.model.params.SystemInfoParam;

/**
 * 系统信息接口
 * @author sukidayo
 * @date 2024/2/10 7:49
 */
public interface SystemInfoService extends IService<SystemInfo> {

    /**
     * 分页查询所有的系统信息
     *
     * @param pageQueryParam 分页查询的参数
     * @return 返回系统信息列表对象
     */
    IPage<SystemInfoDTO> listSystemInfo(PageQueryParam pageQueryParam);

    /**
     * 新增一个系统信息
     *
     * @param systemInfoParam 系统信息参数不为null
     */
    void saveSystemInfo(SystemInfoParam systemInfoParam);

}
