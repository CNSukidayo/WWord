package cnsukidayo.com.gitee.dao;

import cnsukidayo.com.gitee.model.pojo.User;
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
