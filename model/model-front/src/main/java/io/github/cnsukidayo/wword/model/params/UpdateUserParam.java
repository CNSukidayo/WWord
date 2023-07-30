package io.github.cnsukidayo.wword.model.params;


import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.enums.SexType;
import io.github.cnsukidayo.wword.model.pojo.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * 登陆参数,如果参数是空并且没有加以约束,则该会将用户对应的字段设置以为空.
 *
 * @author cnsukidayo
 * @date 2023/5/17 20:14
 */
@Schema(description = "更新用户个人信息请求体")
public class UpdateUserParam implements InputConverter<User> {

    @Schema(description = "绑定邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(max = 255, message = "邮箱长度不能超过 {max}")
    private String email;

    @Schema(description = "用户昵称")
    @NotBlank(message = "昵称不能为空")
    @Size(max = 32, message = "昵称长度不能超过 {max}")
    private String nick;

    @Schema(description = "个人描述信息")
    @Size(max = 255, message = "个人描述不能超过 {max} 个字符")
    private String describeInfo;

    @Schema(description = "个人性别")
    private SexType sex;

    @Schema(description = "生日")
    private LocalDate birthday;

    @Schema(description = "大学")
    private String university;

    public UpdateUserParam() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    public void setDescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo;
    }

    public SexType getSex() {
        return sex;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
