package cnsukidayo.com.gitee.dao;

import cnsukidayo.com.gitee.model.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author sukidayo
 * @date 2023/5/17 15:34
 */

@Mapper
public interface StudentMapper {

    Student selectStudentByID(Integer id);

}
