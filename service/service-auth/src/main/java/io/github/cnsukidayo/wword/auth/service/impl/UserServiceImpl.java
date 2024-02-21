package io.github.cnsukidayo.wword.auth.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.auth.dao.UserMapper;
import io.github.cnsukidayo.wword.auth.dao.UserRoleMapper;
import io.github.cnsukidayo.wword.auth.event.login.LoginEvent;
import io.github.cnsukidayo.wword.auth.service.PermissionService;
import io.github.cnsukidayo.wword.auth.service.RolePermissionService;
import io.github.cnsukidayo.wword.auth.service.UserService;
import io.github.cnsukidayo.wword.common.security.authentication.Authentication;
import io.github.cnsukidayo.wword.common.security.context.SecurityContextHolder;
import io.github.cnsukidayo.wword.common.utils.RedisUtils;
import io.github.cnsukidayo.wword.common.utils.SecurityUtils;
import io.github.cnsukidayo.wword.common.utils.ServletUtils;
import io.github.cnsukidayo.wword.core.client.CoreFeignClient;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.model.bo.UserPermissionBO;
import io.github.cnsukidayo.wword.model.entity.Permission;
import io.github.cnsukidayo.wword.model.entity.RolePermission;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.entity.UserRole;
import io.github.cnsukidayo.wword.model.enums.LoginType;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.model.params.*;
import io.github.cnsukidayo.wword.model.token.AuthToken;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author sukidayo
 * @date 2023/9/7 18:37
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final RedisTemplate<String, String> redisTemplate;

    private final CoreFeignClient coreFeignClient;

    private final ApplicationEventPublisher eventPublisher;

    private final UserRoleMapper userRoleMapper;

    private final PermissionService permissionService;

    private final RolePermissionService rolePermissionService;

    private final AntPathMatcher antPathMatcher;

    public UserServiceImpl(RedisTemplate<String, String> redisTemplate,
                           ApplicationEventPublisher applicationEventPublisher,
                           CoreFeignClient coreFeignClient,
                           UserRoleMapper userRoleMapper,
                           PermissionService permissionService,
                           RolePermissionService rolePermissionService,
                           AntPathMatcher antPathMatcher) {
        this.redisTemplate = redisTemplate;
        this.coreFeignClient = coreFeignClient;
        this.eventPublisher = applicationEventPublisher;
        this.userRoleMapper = userRoleMapper;
        this.permissionService = permissionService;
        this.rolePermissionService = rolePermissionService;
        this.antPathMatcher = antPathMatcher;
    }


    @Override
    public UserPermissionBO getAndAuth(CheckAuthParam checkAuthParam) {
        Assert.notNull(checkAuthParam, "checkAuthParam must not be null");

        // 得到用户id
        String userID = Optional.ofNullable(redisTemplate.opsForValue().get(SecurityUtils.buildTokenAccessKey(checkAuthParam.getToken())))
            .filter(StringUtils::hasText)
            .orElseThrow(() -> new BadRequestException(ResultCodeEnum.LOGIN_STATE_INVALID));
        // 查询目标用户
        User user = Optional.ofNullable(baseMapper.selectById(userID))
            .orElseThrow(() -> new BadRequestException(ResultCodeEnum.LOGIN_FAIL));

        UserPermissionBO userPermissionBO = new UserPermissionBO();
        userPermissionBO.setUser(user);
        userPermissionBO.setHasPermission(false);
        // 查询目标用户的角色id
        List<Long> userRoleIdList = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUuid, user.getUuid()))
            .stream()
            .map(UserRole::getRoleId)
            .toList();
        // 通过缓存直接查询所有接口
        List<Permission> allPermissionList = permissionService.getTraces();
        // 查询目标用户的所有权限接口的id
        List<Long> permissionIdList = rolePermissionService.list(new LambdaQueryWrapper<RolePermission>().in(RolePermission::getRoleId, userRoleIdList))
            .stream()
            .map(RolePermission::getPermissionId)
            .distinct()
            .toList();
        // 筛选出permissionIdList对应的接口
        allPermissionList = allPermissionList.stream()
            .filter(permission -> permissionIdList.contains(permission.getId()))
            .toList();
        // 判断是否有符合权限的接口
        for (Permission permission : allPermissionList) {
            if (antPathMatcher.match(permission.getPath(), checkAuthParam.getTargetUrl())) {
                userPermissionBO.setHasPermission(true);
                return userPermissionBO;
            }
        }
        return userPermissionBO;
    }


    @Override
    public void register(UserRegisterParam userRegisterParam) {
        Assert.notNull(userRegisterParam, "UserRegisterParam must be null");
        // 参数校验总是在最先执行的
        if (!userRegisterParam.getPassword().equals(userRegisterParam.getConfirmPassword()))
            throw new BadRequestException(ResultCodeEnum.PASSWORD_INCONSISTENT);
        Optional.ofNullable(baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getAccount, userRegisterParam.getAccount())))
            .ifPresent(user -> {
                throw new BadRequestException(ResultCodeEnum.ALREADY_EXIST, "用户名已经存在!");
            });
        User user = userRegisterParam.convertTo();
        // 加密密码
        user.setPassword(BCrypt.hashpw(userRegisterParam.getPassword()));
        // 设置默认用户名
        user.setNick(WWordConst.USER_NICK_PREFIX + UUID.randomUUID().toString().substring(0, 8));
        baseMapper.insert(user);
    }

    @Override
    public AuthToken login(LoginParam loginParam) {
        Assert.notNull(loginParam, "LoginParam must not be null");
        if (SecurityContextHolder.getContext().isAuthenticated()) {
            // If the user has been logged in
            throw new BadRequestException(ResultCodeEnum.LOGIN_STATE_INVALID.getCode(),
                "您已登录,请不要重复登录");
        }

        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getAccount, loginParam.getAccount()));
        if (user == null || !BCrypt.checkpw(loginParam.getPassword(), user.getPassword())) {
            throw new BadRequestException(ResultCodeEnum.LOGIN_STATE_INVALID.getCode(),
                "用户名或者密码不正确");
        }
        // 记录登陆事件
        eventPublisher.publishEvent(
            new LoginEvent(this,
                user.getUuid(),
                LoginType.LOGIN_IN,
                ServletUtils.getCurrentRequest().map(request -> request.getHeader(HttpHeaders.USER_AGENT)).orElse("")));
        return buildAuthToken(user);
    }

    @Override
    public void updatePassword(User user, UpdatePasswordParam updatePasswordParam) {
        Assert.notNull(user, "user must not be null");
        Assert.notNull(updatePasswordParam, "updatePasswordParam must not be null");
        if (!updatePasswordParam.getNewPassword().equals(updatePasswordParam.getConfirmNewPassword()))
            throw new BadRequestException(ResultCodeEnum.PASSWORD_INCONSISTENT);

        if (!BCrypt.checkpw(updatePasswordParam.getOldPassword(), user.getPassword()))
            throw new BadRequestException(ResultCodeEnum.OLD_PASSWORD_ERROR);
        // 加密密码
        user.setPassword(BCrypt.hashpw(updatePasswordParam.getNewPassword()));
        // 更新
        baseMapper.updateById(user);
        // 记录密码更新事件
        eventPublisher.publishEvent(
            new LoginEvent(this,
                user.getUuid(),
                LoginType.PASSWORD_UPDATE,
                ServletUtils.getCurrentRequest().map(request -> request.getHeader(HttpHeaders.USER_AGENT)).orElse("")));
    }

    @Override
    public AuthToken refreshToken(String refreshToken) {
        Assert.hasText(refreshToken, "Refresh token must not be blank");
        // 从缓存中得到用户的refresh_token,如果找不到说明refresh_token已经过期,用户很长时间没有登陆了,需要用户重新登陆
        String userID = redisTemplate.opsForValue().get(SecurityUtils.buildTokenRefreshKey(refreshToken));
        if (userID == null) {
            throw new BadRequestException(ResultCodeEnum.LOGIN_STATE_INVALID);
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
    public void update(UpdateUserParam updateUserParam, User user) {
        Assert.notNull(updateUserParam, "UpdateUserParam must not be null");
        Assert.notNull(user, "User must not be null");
        // 判断学校是否是数据库中存在的学校
        if (StringUtils.hasText(updateUserParam.getUniversity()) && !coreFeignClient.hasUniversity(updateUserParam.getUniversity())) {
            throw new BadRequestException(ResultCodeEnum.NOT_EXISTS.getCode(),
                "学校:" + updateUserParam.getUniversity() + "不存在!");
        }
        // 为null的字段mybatis默认是不会更新的
        User update = updateUserParam.convertTo();
        update.setUuid(user.getUuid());
        baseMapper.updateById(update);
    }

    @Override
    public void clearToken() {
        // 判断当前用户是否已经登陆
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new BadRequestException(ResultCodeEnum.NO_LOGIN);
        }

        // 得到当前的用户
        User user = authentication.user();

        // 清除令牌
        clearToken(user);

        // 记录退出登陆事件
        eventPublisher.publishEvent(
            new LoginEvent(this,
                user.getUuid(),
                LoginType.LOGIN_OUT,
                ServletUtils.getCurrentRequest().map(request -> request.getHeader(HttpHeaders.USER_AGENT)).orElse("")));
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

        // 创建新的token
        AuthToken token = new AuthToken();

        token.setAccessToken(UUID.randomUUID().toString().replaceAll("-", ""));
        token.setExpiredIn((int) RedisUtils.ONE_DAY);
        token.setRefreshToken(UUID.randomUUID().toString().replaceAll("-", ""));

        // 缓存这些令牌仅仅是为了清除令牌
        redisTemplate.opsForValue().set(SecurityUtils.buildAccessTokenKey(user), token.getAccessToken(),
            RedisUtils.ONE_DAY, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(SecurityUtils.buildRefreshTokenKey(user), token.getRefreshToken(),
            RedisUtils.ONE_MONTH, TimeUnit.SECONDS);

        // 缓存这些令牌,通过令牌获取用户的ID
        redisTemplate.opsForValue().set(SecurityUtils.buildTokenAccessKey(token.getAccessToken()), String.valueOf(user.getUuid()),
            RedisUtils.ONE_DAY, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(SecurityUtils.buildTokenRefreshKey(token.getRefreshToken()), String.valueOf(user.getUuid()),
            RedisUtils.ONE_MONTH, TimeUnit.SECONDS);

        return token;
    }

    private void clearToken(User user) {
        Assert.notNull(user, "user must not be null");
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


}
