package io.github.cnsukidayo.wword.core.config;

import io.github.cnsukidayo.wword.common.config.WWordMvcConfiguration;
import io.github.cnsukidayo.wword.common.config.properties.WWordProperties;
import io.github.cnsukidayo.wword.core.security.resolver.AuthenticationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

@Configuration
public class ServiceCoreMvcConfiguration extends WWordMvcConfiguration {

    public ServiceCoreMvcConfiguration(WWordProperties wWordProperties) {
        super(wWordProperties);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationArgumentResolver());
    }

}
