package io.github.cnsukidayo.wword.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sukidayo
 * @date 2023/5/20 13:42
 */

@ConfigurationProperties(prefix = "wword")
public class WWordProperties {

    private String resourceLocations;

    private Integer maxLoginLog;

    public String getResourceLocations() {
        return resourceLocations;
    }

    public void setResourceLocations(String resourceLocations) {
        this.resourceLocations = resourceLocations;
    }

    public Integer getMaxLoginLog() {
        return maxLoginLog;
    }

    public void setMaxLoginLog(Integer maxLoginLog) {
        this.maxLoginLog = maxLoginLog;
    }
}
