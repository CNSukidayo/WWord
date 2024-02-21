package io.github.cnsukidayo.wword.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.admin.dao.SystemInfoMapper;
import io.github.cnsukidayo.wword.admin.service.SystemInfoService;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.global.utils.JsonUtils;
import io.github.cnsukidayo.wword.model.dto.SystemInfoDTO;
import io.github.cnsukidayo.wword.model.entity.SystemInfo;
import io.github.cnsukidayo.wword.global.support.constant.RedisConstant;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.model.params.PageQueryParam;
import io.github.cnsukidayo.wword.model.params.SystemInfoParam;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author sukidayo
 * @date 2024/2/10 7:50
 */
@Service
public class SystemInfoServiceImpl extends ServiceImpl<SystemInfoMapper, SystemInfo> implements SystemInfoService {

    private final StringRedisTemplate redisTemplate;

    public SystemInfoServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public IPage<SystemInfoDTO> listSystemInfo(PageQueryParam pageQueryParam) {
        Assert.notNull(pageQueryParam, "pageQueryParam must not be null");
        return baseMapper.selectPage(new Page<>(pageQueryParam.getCurrent() - 1, pageQueryParam.getSize()), null)
            .convert(systemInfo -> new SystemInfoDTO().convertFrom(systemInfo));
    }

    @Transactional
    @Override
    public void saveSystemInfo(SystemInfoParam systemInfoParam) {
        Assert.notNull(systemInfoParam, "systemInfoParam must not be null");
        SystemInfo systemInfo = baseMapper.selectOne(new LambdaQueryWrapper<SystemInfo>().eq(SystemInfo::getSystemInfoType, systemInfoParam.getSystemInfoType()));
        if (systemInfo != null && !systemInfo.getId().equals(systemInfoParam.getId())) {
            throw new BadRequestException(ResultCodeEnum.ALREADY_EXIST, "指定的系统信息已经存在!");
        } else {
            SystemInfo entity = systemInfoParam.convertTo();
            redisTemplate.opsForValue().set(String.format(RedisConstant.SYSTEM_INFO, entity.getSystemInfoType().name()),
                JsonUtils.objectToJson(entity));
            this.saveOrUpdate(entity);
        }
    }

}
