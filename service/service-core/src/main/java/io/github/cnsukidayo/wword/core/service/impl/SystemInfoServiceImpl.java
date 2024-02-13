package io.github.cnsukidayo.wword.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.model.environment.RedisConstant;
import io.github.cnsukidayo.wword.core.dao.SystemInfoMapper;
import io.github.cnsukidayo.wword.core.service.SystemInfoService;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.global.utils.JsonUtils;
import io.github.cnsukidayo.wword.model.entity.SystemInfo;
import io.github.cnsukidayo.wword.model.enums.SystemInfoType;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author sukidayo
 * @date 2024/2/8 19:37
 */
@Service
public class SystemInfoServiceImpl extends ServiceImpl<SystemInfoMapper, SystemInfo> implements SystemInfoService {

    private final StringRedisTemplate redisTemplate;

    public SystemInfoServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getSystemInfoContext(SystemInfoType systemInfoType) {
        Assert.notNull(systemInfoType, "systemInfoType must not be null");
        String systemInfoJson = redisTemplate.opsForValue().get(String.format(RedisConstant.SYSTEM_INFO, systemInfoType.name()));
        if (systemInfoJson != null) {
            return JsonUtils.jsonToObject(systemInfoJson, SystemInfo.class).getContext();
        }
        // 缓存查询不到则从数据库中查询
        SystemInfo systemInfo = baseMapper.selectOne(new LambdaQueryWrapper<SystemInfo>().eq(SystemInfo::getSystemInfoType, systemInfoType));
        if (systemInfo != null) {
            redisTemplate.opsForValue().set(String.format(RedisConstant.SYSTEM_INFO, systemInfoType.name()), JsonUtils.objectToJson(systemInfo));
            return systemInfo.getContext();
        }
        throw new BadRequestException(ResultCodeEnum.NOT_EXISTS);
    }


}
