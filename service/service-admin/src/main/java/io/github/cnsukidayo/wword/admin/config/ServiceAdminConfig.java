package io.github.cnsukidayo.wword.admin.config;

import io.github.cnsukidayo.wword.admin.support.DefaultWordIdComparable;
import io.github.cnsukidayo.wword.core.client.CoreFeignClient;
import io.github.cnsukidayo.wword.model.entity.WordId;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

/**
 * @author sukidayo
 * @date 2023/9/10 14:07
 */
@Configuration
public class ServiceAdminConfig {

    @Bean
    @ConditionalOnMissingBean(name = "wordIdComparator")
    public Comparator<WordId> wordIdComparator(CoreFeignClient coreFeignClient) {
        return new DefaultWordIdComparable(coreFeignClient);
    }

}
