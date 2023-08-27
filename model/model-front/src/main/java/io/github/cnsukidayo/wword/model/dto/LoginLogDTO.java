package io.github.cnsukidayo.wword.model.dto;

import io.github.cnsukidayo.wword.model.base.OutputConverter;
import io.github.cnsukidayo.wword.model.entity.LoginLog;
import io.github.cnsukidayo.wword.model.enums.LoginType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * @author sukidayo
 * @date 2023/8/27 14:54
 */
@Schema(description = "登陆日志对象;用于展示用户登录事件")
public class LoginLogDTO implements OutputConverter<LoginLogDTO, LoginLog> {

    @Schema(description = "用户触发时间时的IP地址")
    private String ipAddress;

    @Schema(description = "此次登陆的类型")
    private LoginType loginType;

    @Schema(description = "上下文信息")
    private String content;

    @Schema(description = "事件发生时间")
    private LocalDateTime createTime;

    public LoginLogDTO() {
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
