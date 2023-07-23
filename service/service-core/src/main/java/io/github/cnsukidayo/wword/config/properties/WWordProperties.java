package io.github.cnsukidayo.wword.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sukidayo
 * @date 2023/5/20 13:42
 */

@ConfigurationProperties(prefix = "wword")
public class WWordProperties {

    private String resourceLocations;

    public String getResourceLocations() {
        return resourceLocations;
    }

    public void setResourceLocations(String resourceLocations) {
        this.resourceLocations = resourceLocations;
    }
}
