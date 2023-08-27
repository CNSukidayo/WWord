package io.github.cnsukidayo.wword.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.entity.base.BaseEntity;
import io.github.cnsukidayo.wword.model.enums.LoginType;

/**
 * @author sukidayo
 * @date 2023/8/27 10:42
 */
@TableName("login_log")
public class LoginLog extends BaseEntity {

    @TableId(value = "uuid")
    private Long UUID;

    @TableField("ip_address")
    private String ipAddress;

    @TableField("log_type")
    private LoginType loginType;

    @TableField("content")
    private String content;

    public LoginLog() {
    }

    public Long getUUID() {
        return UUID;
    }

    public void setUUID(Long UUID) {
        this.UUID = UUID;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
