package io.github.cnsukidayo.wword.core.config;

import io.github.cnsukidayo.wword.core.support.markdown.OriginTextContentNodeRenderer;
import io.github.cnsukidayo.wword.core.support.markdown.OriginTextContentRenderer;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.Renderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sukidayo
 * @date 2024/2/21 8:56
 */
@Configuration
public class CoreConfig {

    @Bean
    public Parser markDownParser() {
        return Parser.builder()
            .build();
    }

    @Bean
    public Renderer markDownRenderer() {
        return OriginTextContentRenderer.builder()
            .nodeRendererFactory(OriginTextContentNodeRenderer::new).
            build();
    }

}
