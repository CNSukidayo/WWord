package io.github.cnsukidayo.wword.model.base;

import io.github.cnsukidayo.wword.model.utils.ReflectionUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * @author sukidayo
 * @date 2023/7/30 16:23
 */
public interface InputConverter<D> {

    @SuppressWarnings("unchecked")
    default D convertTo() {
        // 得到泛型类型,因为目标类可能实现多个接口.现在要找到实现当前接口所对应的泛型类型
        ParameterizedType currentType = ReflectionUtils.getParameterizedType(InputConverter.class, this.getClass());

        // 参数校验
        Objects.requireNonNull(currentType, "Cannot fetch actual type because parameterized type is null");

        Class<D> domainClass = (Class<D>) currentType.getActualTypeArguments()[0];

        return transformFrom(this, domainClass);
    }


    default <T> T transformFrom(Object source, Class<T> targetClass) {
        Objects.requireNonNull(targetClass, "targetClass must not be null");

        if (source == null) {
            return null;
        }

        // 初始化
        try {
            // 目标类的实例对象
            T targetInstance = targetClass.getConstructor().newInstance();
            BeanUtils.copyProperties(source,targetInstance);
            return targetInstance;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Failed to new " + targetClass.getName() + " instance or copy properties", e);
        }
    }

}
