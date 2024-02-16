package io.github.cnsukidayo.wword.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author sukidayo
 * @date 2024/2/15 10:44
 */
@Target({PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface ValidList {

    Class<?>[] value() default {};

}
