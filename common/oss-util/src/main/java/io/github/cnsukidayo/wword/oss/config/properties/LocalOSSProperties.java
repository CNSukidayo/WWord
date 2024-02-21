package io.github.cnsukidayo.wword.oss.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sukidayo
 * @date 2024/2/19 19:54
 */
@ConfigurationProperties("wword.oss.local")
public class LocalOSSProperties {

    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    /**
     * 设置上传路径
     */
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

}