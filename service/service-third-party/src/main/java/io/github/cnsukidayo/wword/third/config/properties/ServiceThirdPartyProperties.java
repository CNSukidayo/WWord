package io.github.cnsukidayo.wword.third.config.properties;

import io.github.cnsukidayo.wword.third.oss.config.properties.OSSProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sukidayo
 * @date 2023/9/10 10:44
 */
@ConfigurationProperties(prefix = "service-third-party")
public class ServiceThirdPartyProperties {

    private OSSProperties ossProperties;

    public ServiceThirdPartyProperties() {
    }

    public OSSProperties getOssProperties() {
        return ossProperties;
    }

    public void setOssProperties(OSSProperties ossProperties) {
        this.ossProperties = ossProperties;
    }
}
