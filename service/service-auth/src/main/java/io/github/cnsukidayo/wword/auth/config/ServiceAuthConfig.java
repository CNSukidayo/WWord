package io.github.cnsukidayo.wword.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

/**
 * @author sukidayo
 * @date 2023/9/9 19:36
 */
@Configuration
public class ServiceAuthConfig {

    @Bean
    public AntPathMatcher antPathMatcher(){
        return new AntPathMatcher();
    }

}
