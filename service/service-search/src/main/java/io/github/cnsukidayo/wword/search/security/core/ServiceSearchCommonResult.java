package io.github.cnsukidayo.wword.search.security.core;

import io.github.cnsukidayo.wword.common.core.AbstractResultControllerAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author sukidayo
 * @date 2023/9/14 11:00
 */
@ControllerAdvice(value = {"io.github.cnsukidayo.wword.search.controller"})
public class ServiceSearchCommonResult extends AbstractResultControllerAdvice {
}
