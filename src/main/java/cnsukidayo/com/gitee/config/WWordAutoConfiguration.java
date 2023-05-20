package cnsukidayo.com.gitee.config;

import cnsukidayo.com.gitee.config.properties.WWordProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author sukidayo
 * @date 2023/5/20 13:47
 */
@Configuration
@EnableConfigurationProperties(value = WWordProperties.class)
@EnableCaching
public class WWordAutoConfiguration implements WebMvcConfigurer {

    private final WWordProperties wWordProperties;
    private static final String FILE_PROTOCOL = "file:///";

    public WWordAutoConfiguration(WWordProperties wWordProperties) {
        this.wWordProperties = wWordProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(FILE_PROTOCOL + wWordProperties.getResourceLocations());
    }
}
