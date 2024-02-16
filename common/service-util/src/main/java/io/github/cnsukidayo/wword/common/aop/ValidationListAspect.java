package io.github.cnsukidayo.wword.common.aop;

import io.github.cnsukidayo.wword.common.annotation.ValidList;
import io.github.cnsukidayo.wword.common.utils.ValidationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 校验参数列表的切面
 */
@Component
@Aspect
public class ValidationListAspect {

    @Pointcut("execution(* io.github.cnsukidayo.wword.*.controller..*(.., @io.github.cnsukidayo.wword.common.annotation.ValidList (*), ..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object validateInput(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法的签名信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取方法
        Method method = signature.getMethod();
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        // 获取方法参数上的注解信息
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation.annotationType() == ValidList.class) {
                    // 参数被 ValidList 注解修饰
                    ValidList validList = (ValidList) annotation;
                    Object arg = args[i];
                    if (arg instanceof List<?> requestParamList) {
                        for (Object requestParam : requestParamList) {
                            if (validList.value() != null) {
                                ValidationUtils.validate(requestParam, validList.value());
                            } else {
                                ValidationUtils.validate(requestParam);
                            }
                        }
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}