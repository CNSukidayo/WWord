package io.github.cnsukidayo.wword.auth.listener.login;

import io.github.cnsukidayo.wword.auth.event.login.LoginEvent;
import io.github.cnsukidayo.wword.auth.service.LoginLogService;
import io.github.cnsukidayo.wword.model.entity.LoginLog;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author sukidayo
 * @date 2023/8/27 11:17
 */
@Component
public class LoginEventListener {

    private final LoginLogService loginLogService;

    public LoginEventListener(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @EventListener
    @Async
    public void onApplicationEvent(LoginEvent event) {
        LoginLog loginLog = event.getLoginLog();
        // 创建日志
        loginLogService.save(loginLog);
    }

}
