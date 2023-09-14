package io.github.cnsukidayo.wword.search.security.core;

import io.github.cnsukidayo.wword.common.core.AbstractControllerExceptionHandler;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author sukidayo
 * @date 2023/9/14 11:16
 */
@RestControllerAdvice(value = {
    "io.github.cnsukidayo.wword.search.controller",
    "io.github.cnsukidayo.wword.search.api"
})
public class ServiceSearchControllerExceptionHandler extends AbstractControllerExceptionHandler {
    public ServiceSearchControllerExceptionHandler(@Value("${knife4j.production}") Boolean production) {
        super(production, LoggerFactory.getLogger(ServiceSearchControllerExceptionHandler.class));
    }
}
