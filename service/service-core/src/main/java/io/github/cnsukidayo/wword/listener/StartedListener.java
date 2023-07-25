package io.github.cnsukidayo.wword.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author sukidayo
 * @date 2023/7/23 15:06
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Value("${server.port}")
    private String port;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("swagger地址:https://localhost:" + port + "/swagger-ui/index.html");
    }
}
