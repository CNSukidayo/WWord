package io.github.cnsukidayo.wword.core.event.login;

import io.github.cnsukidayo.wword.common.utils.ServletUtils;
import io.github.cnsukidayo.wword.model.entity.LoginLog;
import io.github.cnsukidayo.wword.model.enums.LoginType;
import org.springframework.context.ApplicationEvent;

/**
 * @author sukidayo
 * @date 2023/8/27 10:41
 */
public class LoginEvent extends ApplicationEvent {

    private final LoginLog loginLog;


    public LoginEvent(Object source, Long UUID, LoginType loginType, String content) {
        super(source);
        this.loginLog = new LoginLog();
        this.loginLog.setUuid(UUID);
        this.loginLog.setIpAddress(ServletUtils.getRequestIp());
        this.loginLog.setLoginType(loginType);
        this.loginLog.setContent(content);
    }

    public LoginLog getLoginLog() {
        return loginLog;
    }
}
