package io.github.cnsukidayo.wword.config;

import io.github.cnsukidayo.wword.config.properties.WWordProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/5/20 13:47
 */
@Configuration
@EnableConfigurationProperties(value = WWordProperties.class)
@EnableCaching
@Import(value = HttpMessageConvertersAutoConfiguration.class)
public class WWordAutoConfiguration implements WebMvcConfigurer {

    private final WWordProperties wWordProperties;
    private static final String FILE_PROTOCOL = "file:///";

    public WWordAutoConfiguration(WWordProperties wWordProperties) {
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
                    Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
                    JsonComponentModule module = new JsonComponentModule();
                    ObjectMapper objectMapper = builder.modules(module).build();
                    mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
                });
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(FILE_PROTOCOL + wWordProperties.getResourceLocations());
    }
}
