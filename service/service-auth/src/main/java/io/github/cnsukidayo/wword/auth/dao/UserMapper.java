package io.github.cnsukidayo.wword.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author sukidayo
 * @date 2023/9/7 18:38
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
