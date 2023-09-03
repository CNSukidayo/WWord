package io.github.cnsukidayo.wword.model.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class ReflectionUtils {

    private ReflectionUtils() {
    }

    /**
     * 得到泛型类型
     *
     * @param superType    父类必须不为null
     * @param genericTypes 泛型类型必须不为null
     * @return 返回值为接口的类型, 如果不匹配则为null
     */
    public static ParameterizedType getParameterizedType(Class<?> superType,
                                                         Type... genericTypes) {
        Objects.requireNonNull(superType, "Cannot fetch actual type because parameterized type is null");

        ParameterizedType currentType = null;

        for (Type genericType : genericTypes) {
            if (genericType instanceof ParameterizedType) {
                if (((ParameterizedType) genericType).getRawType().getTypeName().equals(superType.getTypeName())) {
                    currentType = (ParameterizedType) genericType;
                    break;
                }
            }
        }

        return currentType;
    }

    /**
     * 得到泛型类型
     *
     * @param interfaceType       接口类型参数必须不为null
     * @param implementationClass 接口类参数必须不为null
     * @return 返回值为接口的类型, 如果不匹配则为null
     */
    public static ParameterizedType getParameterizedType(Class<?> interfaceType,
                                                         Class<?> implementationClass) {
        Objects.requireNonNull(interfaceType, "Cannot fetch actual type because parameterized type is null");
        if (!interfaceType.isInterface()) {
            throw new IllegalArgumentException("The give type must be an interface");
        }
        if (implementationClass == null) {
            // 如果父类是Object则返回null
            return null;
        }

        // 得到泛型类型
        ParameterizedType currentType =
            getParameterizedType(interfaceType, implementationClass.getGenericInterfaces());

        if (currentType != null) {
            // 返回当前类型
            return currentType;
        }

        Class<?> superclass = implementationClass.getSuperclass();

        return getParameterizedType(interfaceType, superclass);
    }
}
