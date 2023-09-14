package io.github.cnsukidayo.wword.third.security.core;

import io.github.cnsukidayo.wword.common.core.AbstractControllerExceptionHandler;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author sukidayo
 * @date 2023/9/14 11:16
 */
@RestControllerAdvice(value = {
    "io.github.cnsukidayo.wword.third.oss.api",
    "io.github.cnsukidayo.wword.third.oss.controller"
})
public class ServiceThirdControllerExceptionHandler extends AbstractControllerExceptionHandler {
    public ServiceThirdControllerExceptionHandler(@Value("${knife4j.production}") Boolean production) {
        super(production, LoggerFactory.getLogger(ServiceThirdControllerExceptionHandler.class));
    }
}
