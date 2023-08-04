package io.github.cnsukidayo.wword.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动时的回调
 *
 * @author sukidayo
 * @date 2023/7/23 15:06
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${server.port}")
    private String port;

    @Value("${knife4j.enable}")
    private Boolean knife4jEnable;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        this.printStartInfo();
    }

    private void printStartInfo() {
        if (knife4jEnable) {
            log.debug(AnsiOutput
                    .toString(AnsiColor.BRIGHT_GREEN, "WWord api documentation was enabled at  ", "https://localhost:" + port,
                            "/doc.html"));
        }
        log.info(AnsiOutput.toString(AnsiColor.BRIGHT_GREEN, applicationName + " has started successfully!"));
    }

}
