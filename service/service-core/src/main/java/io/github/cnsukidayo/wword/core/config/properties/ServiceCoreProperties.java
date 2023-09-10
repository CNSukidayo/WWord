package io.github.cnsukidayo.wword.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author sukidayo
 * @date 2023/9/10 10:44
 */
@Component
@ConfigurationProperties(prefix = "service-core")
public class ServiceCoreProperties {

    private String remoteBaseAddress;

    public String getRemoteBaseAddress() {
        return remoteBaseAddress;
    }

    public void setRemoteBaseAddress(String remoteBaseAddress) {
        this.remoteBaseAddress = remoteBaseAddress;
    }
}
