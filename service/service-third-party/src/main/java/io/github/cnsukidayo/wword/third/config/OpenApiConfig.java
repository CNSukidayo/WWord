package io.github.cnsukidayo.wword.third.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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
            .info(new Info()
                .title("万语词后端项目API")
                .description("基于SpringDoc的万语词后端项目API;当前模块:三方服务模块")
                .version("v0.0.1"));
    }
}
