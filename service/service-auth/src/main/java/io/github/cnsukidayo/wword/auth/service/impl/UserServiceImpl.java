package io.github.cnsukidayo.wword.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.auth.dao.UserMapper;
import io.github.cnsukidayo.wword.auth.service.UserService;
import io.github.cnsukidayo.wword.common.utils.SecurityUtils;
import io.github.cnsukidayo.wword.model.bo.UserPermissionBO;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.params.CheckAuthParam;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author sukidayo
 * @date 2023/9/7 18:37
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final RedisTemplate<String, String> redisTemplate;

    public UserServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserPermissionBO getAndAuth(CheckAuthParam checkAuthParam) {
        Assert.notNull(checkAuthParam, "checkAuthParam must not be null");

        // 得到用户id
        String userID = redisTemplate.opsForValue().get(SecurityUtils.buildTokenAccessKey(checkAuthParam.getToken()));

        // 查询目标用户
        User user = baseMapper.selectById(userID);
        UserPermissionBO userPermissionBO = new UserPermissionBO();
        userPermissionBO.setUser(user);
        userPermissionBO.setHasPermission(true);
        return userPermissionBO;
    }
}
