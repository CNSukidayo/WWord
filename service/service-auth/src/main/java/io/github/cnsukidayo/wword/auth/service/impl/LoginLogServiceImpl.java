package io.github.cnsukidayo.wword.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.auth.dao.LoginLogMapper;
import io.github.cnsukidayo.wword.auth.service.LoginLogService;
import io.github.cnsukidayo.wword.model.entity.LoginLog;
import io.github.cnsukidayo.wword.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author sukidayo
 * @date 2023/8/27 11:19
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Override
    public IPage<LoginLog> getLoginLog(User user, Page<LoginLog> pageParam) {
        Assert.notNull(user, "user must not be null");
        Assert.notNull(pageParam, "pageParam must not be null");

        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<LoginLog>()
                .eq(LoginLog::getUuid, user.getUuid())
                .orderByDesc(LoginLog::getCreateTime));
    }

}
