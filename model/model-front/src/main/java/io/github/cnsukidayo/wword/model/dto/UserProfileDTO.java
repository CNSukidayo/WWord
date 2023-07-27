package io.github.cnsukidayo.wword.model.dto;

import io.github.cnsukidayo.wword.model.enums.AccountCreateProgress;
import io.github.cnsukidayo.wword.model.enums.SexType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * @author sukidayo
 * @date 2023/7/23 15:25
 */
@Schema(description = "用户个人信息")
public class UserProfileDTO {

    @Schema(description = "用户唯一标识")
    private Long UUID;

    @Schema(description = "当前账号绑定的邮箱")
    private String email;

    @Schema(description = "用户名")
    private String account;

    @Schema(description = "头像URL")
    private String avatars;

    @Schema(description = "用户昵称")
    private String nick;

    @Schema(description = "个人简介")
    private String describeInfo;

    @Schema(description = "性别的类型")
    private SexType sex;

    @Schema(description = "生日")
    private LocalDate birthday;

    @Schema(description = "大学")
    private String university;

    @Schema(description = "用户等级")
    private Integer level;

    @Schema(description = "账号创建的进度")
    private AccountCreateProgress createProgress;

    public UserProfileDTO() {
    }

    public Long getUUID() {
        return UUID;
    }

    public void setUUID(Long UUID) {
        this.UUID = UUID;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public AccountCreateProgress getCreateProgress() {
        return createProgress;
    }

    public void setCreateProgress(AccountCreateProgress createProgress) {
        this.createProgress = createProgress;
    }
}
