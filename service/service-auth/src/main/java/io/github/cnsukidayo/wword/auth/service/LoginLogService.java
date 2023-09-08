package io.github.cnsukidayo.wword.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.LoginLog;
import io.github.cnsukidayo.wword.model.entity.User;

/**
 * @author sukidayo
 * @date 2023/8/27 11:18
 */
public interface LoginLogService extends IService<LoginLog> {

    /**
     * 分页查询某个用户的登陆日志信息,排序规则为降序排序
     *
     * @param user      目标用户不能为null
     * @param pageParam 分页查询参数不能为null
     * @return 返回值不为null
     */
    IPage<LoginLog> getLoginLog(User user, Page<LoginLog> pageParam);
}
