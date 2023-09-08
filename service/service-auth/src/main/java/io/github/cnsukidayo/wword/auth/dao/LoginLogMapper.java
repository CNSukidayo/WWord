package io.github.cnsukidayo.wword.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.entity.LoginLog;
import org.springframework.stereotype.Repository;

/**
 * @author sukidayo
 * @date 2023/8/27 11:19
 */
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLog> {
}
