package io.github.cnsukidayo.wword.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author sukidayo
 * @date 2023/9/8 15:27
 */
@Component
@ConfigurationProperties(prefix = "service-auth")
public class ServiceAuthProperties {

    private Integer maxLoginLog;

    public Integer getMaxLoginLog() {
        return maxLoginLog;
    }

    public void setMaxLoginLog(Integer maxLoginLog) {
        this.maxLoginLog = maxLoginLog;
    }
}
