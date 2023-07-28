package io.github.cnsukidayo.wword.common.utils;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sukidayo
 * @date 2023/7/26 12:38
 */
public class IBeanUtils {


    /**
     * 更新配置,但不会更新null
     *
     * @param source 参数不为空
     * @param target 参数不为空
     */
    public static void updateProperties(@NonNull Object source, @NonNull Object target) {
        Assert.notNull(source, "source object must not be null");
        Assert.notNull(target, "target object must not be null");
        // 将source中的非空属性拷贝到target中去
        org.springframework.beans.BeanUtils
                .copyProperties(source, target, getNullPropertyNames(source));
    }


    /**
     * 得到所有属性字段为空的字段名
     *
     * @param source 参数不为空
     * @return 所有属性字段值是null的String数组
     */
    @NonNull
    private static String[] getNullPropertyNames(@NonNull Object source) {
        return getNullPropertyNameSet(source).toArray(new String[0]);
    }

    /**
     * 得到所有属性字段为空的字段名
     *
     * @param source 参数不为空
     * @return 所有属性字段值是null的Set集合
     */
    @NonNull
    private static Set<String> getNullPropertyNameSet(@NonNull Object source) {
        Assert.notNull(source, "source object must not be null");
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = beanWrapper.getPropertyValue(propertyName);
            // 如果属性的是null,则将它添加到集合中去,Set去重
            if (propertyValue == null) {
                emptyNames.add(propertyName);
            }
        }

        return emptyNames;
    }

}
