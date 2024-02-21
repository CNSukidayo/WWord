package io.github.cnsukidayo.wword.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import io.github.cnsukidayo.wword.oss.component.OSSComponent;
import io.github.cnsukidayo.wword.oss.component.impl.AliOSSComponent;
import io.github.cnsukidayo.wword.oss.component.impl.LocalOSSComponent;
import io.github.cnsukidayo.wword.oss.config.properties.AliOSSProperties;
import io.github.cnsukidayo.wword.oss.config.properties.LocalOSSProperties;
import io.github.cnsukidayo.wword.oss.config.properties.OSSProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sukidayo
 * @date 2023/9/12 15:57
 */
@Configuration
@EnableConfigurationProperties(value = {
    OSSProperties.class,
    AliOSSProperties.class,
    LocalOSSProperties.class
})
public class OSSConfig {

    @Bean
    @ConditionalOnProperty(prefix = "wword.oss", name = "oss-type", havingValue = "ali")
    public OSS ossClient(AliOSSProperties aliOssProperties) {
        return new OSSClientBuilder().build(aliOssProperties.getEndpoint(),
            aliOssProperties.getAccessKey(),
            aliOssProperties.getAccessKeySecret());
    }

    @Bean
    @ConditionalOnProperty(prefix = "wword.oss", name = "oss-type", havingValue = "ali")
    public OSSComponent aliOSSComponent(OSS ossClient,
                                        AliOSSProperties aliOSSProperties) {
        return new AliOSSComponent(ossClient, aliOSSProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "wword.oss", name = "oss-type", havingValue = "local")
    public OSSComponent ossComponent(LocalOSSProperties localOSSProperties) {
        return new LocalOSSComponent(localOSSProperties);
    }


}
