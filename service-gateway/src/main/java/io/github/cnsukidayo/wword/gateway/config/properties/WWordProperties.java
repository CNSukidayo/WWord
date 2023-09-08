package io.github.cnsukidayo.wword.gateway.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author sukidayo
 * @date 2023/5/20 13:42
 */

@ConfigurationProperties(prefix = "wword")
@Component
public class WWordProperties {

    private Set<String> excludeUrlPatterns;

    public Set<String> getExcludeUrlPatterns() {
        return excludeUrlPatterns;
    }

    public void setExcludeUrlPatterns(Set<String> excludeUrlPatterns) {
        this.excludeUrlPatterns = excludeUrlPatterns;
    }
}
