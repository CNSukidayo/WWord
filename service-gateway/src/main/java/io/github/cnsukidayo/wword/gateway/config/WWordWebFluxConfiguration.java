package io.github.cnsukidayo.wword.gateway.config;

import io.github.cnsukidayo.wword.gateway.config.properties.WWordProperties;
import io.github.cnsukidayo.wword.global.support.enums.FileBasePath;
import io.github.cnsukidayo.wword.global.utils.FileUtils;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WWordWebFluxConfiguration implements WebFluxConfigurer {


    private final WWordProperties wWordProperties;
    private static final String FILE_PROTOCOL = "file:///";

    public WWordWebFluxConfiguration(WWordProperties wWordProperties) {
        this.wWordProperties = wWordProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (StringUtils.hasText(wWordProperties.getResourceLocations())) {
            registry.addResourceHandler("/public/**")
                .addResourceLocations(FILE_PROTOCOL +
                    FileUtils.separatorFilePath(WWordConst.separatorChar,
                        wWordProperties.getResourceLocations(),
                        FileBasePath.FileNameSpace.PUBLIC.getBasePath()) +
                    WWordConst.separatorChar);
        }
        registry.addResourceHandler("favicon.ico")
            .addResourceLocations("classpath:/static/");
        // 注册swagger相关的映射信息
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html")
            .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
