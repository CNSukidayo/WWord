package io.github.cnsukidayo.wword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.cnsukidayo.wword.model.enums.AccountCreateProgress;
import io.github.cnsukidayo.wword.model.enums.SexType;
import io.github.cnsukidayo.wword.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.Hidden;

import java.io.Serial;
import java.time.LocalDate;

/**
 * @author sukidayo
 * @date 2023/5/17 19:30
 */
@TableName(value = "user")
@Hidden
public class User extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "uuid", type = IdType.AUTO)
    private Long uuid;

    @TableField("email")
    private String email;

    @TableField("account")
    private String account;

    @TableField("password")
    private String password;

    @TableField("avatars")
    private String avatars;

    @TableField(value = "nick")
    private String nick;

    @TableField("describe_info")
    private String describeInfo;

    @TableField(value = "sex")
    private SexType sex;

    @TableField("create_progress")
    private AccountCreateProgress createProgress;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("birthday")
    private LocalDate birthday;

    @TableField("university")
    private String university;

    @TableField("level")
    private Integer level;

    public User() {
    }


    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAvatars() {
        return avatars;
    }

    public void setAvatars(String avatars) {
        this.avatars = avatars;
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

    public AccountCreateProgress getCreateProgress() {
        return createProgress;
    }

    public void setCreateProgress(AccountCreateProgress createProgress) {
        this.createProgress = createProgress;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
