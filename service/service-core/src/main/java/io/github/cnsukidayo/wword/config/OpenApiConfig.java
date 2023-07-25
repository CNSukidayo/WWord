package io.github.cnsukidayo.wword.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 访问地址:<a href="http://localhost:8080/swagger-ui/index.html">http://localhost:8080/swagger-ui/index.html</a>
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("万语词后端项目API")
                        .description("基于SpringDoc的万语词后端项目API")
                        .version("v0.0.1"));
    }

    @Bean
    public GroupedOpenApi testApi() {
        return GroupedOpenApi.builder()
                .displayName("用户端接口")
                .group("user")
                .packagesToScan("io.github.cnsukidayo.wword.controller.u")
                .build();
    }

}
