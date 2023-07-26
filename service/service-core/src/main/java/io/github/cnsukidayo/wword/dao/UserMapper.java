package io.github.cnsukidayo.wword.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * @author sukidayo
 * @date 2023/5/17 15:34
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
