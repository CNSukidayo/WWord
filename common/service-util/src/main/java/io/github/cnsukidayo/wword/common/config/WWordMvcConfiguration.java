package io.github.cnsukidayo.wword.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.github.cnsukidayo.wword.common.config.properties.WWordProperties;
import io.github.cnsukidayo.wword.common.security.resolver.AuthenticationArgumentResolver;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author sukidayo
 * @date 2023/5/20 13:47
 */
@Configuration
@EnableConfigurationProperties(value = WWordProperties.class)
@EnableCaching
@Import(value = HttpMessageConvertersAutoConfiguration.class)
public class WWordMvcConfiguration implements WebMvcConfigurer {

    private final WWordProperties wWordProperties;
    private static final String FILE_PROTOCOL = "file:///";

    public WWordMvcConfiguration(WWordProperties wWordProperties) {
        this.wWordProperties = wWordProperties;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream()
            .filter(c -> c instanceof MappingJackson2HttpMessageConverter)
            .findFirst()
            .ifPresent(converter -> {
                MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
                    (MappingJackson2HttpMessageConverter) converter;
                // 构建ObjectMapper的对象
                Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
                // Json组件模块
                JavaTimeModule module = new JavaTimeModule();
                // 添加自定义解析器
                module.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
                module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                ObjectMapper objectMapper = builder.modules(module).build();
                mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
            });
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (StringUtils.hasText(wWordProperties.getResourceLocations())) {
            registry.addResourceHandler("/static/**")
                .addResourceLocations(FILE_PROTOCOL + wWordProperties.getResourceLocations());
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

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationArgumentResolver());
    }



}
