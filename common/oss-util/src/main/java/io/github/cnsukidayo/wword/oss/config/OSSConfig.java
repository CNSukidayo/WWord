package io.github.cnsukidayo.wword.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import io.github.cnsukidayo.wword.oss.config.properties.OSSProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sukidayo
 * @date 2023/9/12 15:57
 */
@Configuration
@EnableConfigurationProperties(OSSProperties.class)
public class OSSConfig {

    private final OSSProperties ossProperties;

    public OSSConfig(OSSProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public OSS ossClient() {
        return new OSSClientBuilder().build(ossProperties.getEndpoint(),
            ossProperties.getAccessKey(),
            ossProperties.getAccessKeySecret());
    }

}
