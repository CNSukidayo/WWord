package io.github.cnsukidayo.wword.oss.config.properties;

import io.github.cnsukidayo.wword.global.support.enums.OSSType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sukidayo
 * @date 2023/9/12 16:19
 */
@ConfigurationProperties(prefix = "wword.oss")
public class OSSProperties {

    private OSSType ossType;

    private String workSpaceDir;

    public OSSType getOssType() {
        return ossType;
    }

    /**
     * 当前使用的oss类型
     *
     * @param ossType 当前使用的oss类型
     */
    public void setOssType(OSSType ossType) {
        this.ossType = ossType;
    }

    public String getWorkSpaceDir() {
        return workSpaceDir;
    }

    /**
     * 设置上传工作目录路径
     */
    public void setWorkSpaceDir(String workSpaceDir) {
        this.workSpaceDir = workSpaceDir;
    }

}
