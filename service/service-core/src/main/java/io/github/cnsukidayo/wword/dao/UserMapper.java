package io.github.cnsukidayo.wword.dao;

import io.github.cnsukidayo.wword.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author sukidayo
 * @date 2023/5/17 15:34
 */

@Mapper
public interface UserMapper {

    User getUserByID(Integer id);

    User getUserByName(String name);

}
