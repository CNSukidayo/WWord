package io.github.cnsukidayo.wword.oss.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sukidayo
 * @date 2023/9/12 16:19
 */
@ConfigurationProperties(prefix = "service-third-party.oss")
public class OSSProperties {

    private String endpoint;

    private String accessKey;

    private String accessKeySecret;

    private String bucketName;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

}
