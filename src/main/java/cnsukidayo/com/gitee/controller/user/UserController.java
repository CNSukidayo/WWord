package cnsukidayo.com.gitee.controller.user;

import cnsukidayo.com.gitee.dao.StudentMapper;
import cnsukidayo.com.gitee.model.pojo.Student;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sukidayo
 * @date 2023/5/17 20:14
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    private StudentMapper studentMapper;

    public UserController(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @GetMapping("/getStudent")
    @Operation(summary = "获取所有品牌列表",description = "需要登录后访问")
    public Student getStudent() {
        return studentMapper.selectStudentByID(1);
    }

    @GetMapping("/login")
    public Student login() {
        return studentMapper.selectStudentByID(1);
    }


}
