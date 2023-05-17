package cnsukidayo.com.gitee.controller;

import cnsukidayo.com.gitee.dao.StudentMapper;
import cnsukidayo.com.gitee.model.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/5/17 15:13
 */
@RestController
public class Ping {

    @Autowired
    private StudentMapper studentMapper;

    public Ping(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @GetMapping("/ping")
    public Student ping() {
        return studentMapper.selectStudentByID(1);
    }

}
