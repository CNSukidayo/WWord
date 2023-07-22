package cnsukidayo.com.gitee.service.impl;

import cnsukidayo.com.gitee.dao.UserMapper;
import cnsukidayo.com.gitee.exception.BadRequestException;
import cnsukidayo.com.gitee.security.authentication.Authentication;
import cnsukidayo.com.gitee.security.context.SecurityContextHolder;
import cnsukidayo.com.gitee.security.token.AuthToken;
import cnsukidayo.com.gitee.security.util.SecurityUtils;
import cnsukidayo.com.gitee.service.UserService;
import io.github.cnsukidayo.wword.params.LoginParam;
import io.github.cnsukidayo.wword.pojo.User;
import io.github.cnsukidayo.wword.support.WWordConst;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author sukidayo
 * @date 2023/5/19 18:11
 */
@Service
public class UserServiceImpl implements UserService {

    private final RedisTemplate<String, String> redisTemplate;
    private final UserMapper userMapper;

    public UserServiceImpl(RedisTemplate<String, String> redisTemplate,
                           UserMapper userMapper) {
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public AuthToken refreshToken(String refreshToken) {
        Assert.hasText(refreshToken, "Refresh token must not be blank");
        // 从缓存中得到用户的refresh_token,如果找不到说明refresh_token已经过期,用户很长时间没有登陆了,需要用户重新登陆
        String userID = redisTemplate.opsForValue().get(SecurityUtils.buildTokenRefreshKey(refreshToken));
        if (userID == null) {
            throw new BadRequestException("登录状态已失效，请重新登录").setErrorData(refreshToken);
        }
        // 通过refresh_token找到用户的ID,通过用户的ID找到用户的access_token 然后一并清除用户的access_token和refresh_token
        User user = getById(Integer.valueOf(userID));
        // 删除用户的所有令牌信息
        String access_token = redisTemplate.opsForValue().get(SecurityUtils.buildAccessTokenKey(user));
        if (access_token != null) {
            redisTemplate.delete(SecurityUtils.buildTokenAccessKey(access_token));
        }
        redisTemplate.delete(SecurityUtils.buildTokenRefreshKey(refreshToken));
        redisTemplate.delete(SecurityUtils.buildAccessTokenKey(user));
        redisTemplate.delete(SecurityUtils.buildRefreshTokenKey(user));
        return buildAuthToken(user);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getUserByID(id);
    }

    @Override
    public AuthToken login(LoginParam loginParam) {
        Assert.notNull(loginParam, "LoginParam must not be null");

        User user = userMapper.getUserByName(loginParam.getUsername());

        if (SecurityContextHolder.getContext().isAuthenticated()) {
            // If the user has been logged in
            throw new BadRequestException("您已登录，请不要重复登录");
        }

        if (user == null || !user.getPassword().equals(loginParam.getPassword())) {
            throw new BadRequestException("用户名或者密码不正确");
        }

        return buildAuthToken(user);
    }

    @Override
    public User getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.user();
        }
        throw new BadRequestException("未登录，请登录后访问");
    }

    @Override
    public void clearToken() {
        // 判断当前用户是否已经登陆
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new BadRequestException("您尚未登录，因此无法注销");
        }

        // 得到当前的用户
        User user = authentication.user();

        // 清除认证令牌
        Optional.ofNullable(redisTemplate.opsForValue().get(SecurityUtils.buildAccessTokenKey(user))).ifPresent(access_token -> {
            redisTemplate.delete(SecurityUtils.buildTokenAccessKey(access_token));
            redisTemplate.delete(SecurityUtils.buildAccessTokenKey(user));
        });
        // 清除缓存令牌
        Optional.ofNullable(redisTemplate.opsForValue().get(SecurityUtils.buildRefreshTokenKey(user))).ifPresent(refresh_token -> {
            redisTemplate.delete(SecurityUtils.buildTokenRefreshKey(refresh_token));
            redisTemplate.delete(SecurityUtils.buildRefreshTokenKey(user));
        });
    }


    /**
     * 创建授权token
     *
     * @param user 被授权的用户
     * @return 返回授权令牌
     */
    @NonNull
    private AuthToken buildAuthToken(@NonNull User user) {
        Assert.notNull(user, "User must not be null");

        // Generate new token
        AuthToken token = new AuthToken();

        token.setAccessToken(UUID.randomUUID().toString().replaceAll("-", ""));
        token.setExpiredIn(WWordConst.ACCESS_TOKEN_EXPIRED_SECONDS);
        token.setRefreshToken(UUID.randomUUID().toString().replaceAll("-", ""));

        // 缓存这些令牌仅仅是为了清除令牌
        redisTemplate.opsForValue().set(SecurityUtils.buildAccessTokenKey(user), token.getAccessToken(),
                WWordConst.ACCESS_TOKEN_EXPIRED_SECONDS, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(SecurityUtils.buildRefreshTokenKey(user), token.getRefreshToken(),
                WWordConst.REFRESH_TOKEN_EXPIRED_DAYS, TimeUnit.DAYS);

        // 缓存这些令牌,通过令牌获取用户的ID
        redisTemplate.opsForValue().set(SecurityUtils.buildTokenAccessKey(token.getAccessToken()), String.valueOf(user.getId()),
                WWordConst.ACCESS_TOKEN_EXPIRED_SECONDS, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(SecurityUtils.buildTokenRefreshKey(token.getRefreshToken()), String.valueOf(user.getId()),
                WWordConst.REFRESH_TOKEN_EXPIRED_DAYS, TimeUnit.DAYS);

        return token;
    }


}
