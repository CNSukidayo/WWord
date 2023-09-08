package io.github.cnsukidayo.wword.model.base;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

/**
 * @author sukidayo
 * @date 2023/7/30 16:23
 */
public interface OutputConverter<DtoT extends OutputConverter<DtoT, D>, D> {

    /**
     * 将实体对象拷贝为DTO对象
     *
     * @param domain 实体对象不为null
     * @param <T>    DTO对象
     * @return 返回DTO对象
     */
    @SuppressWarnings("unchecked")
    default <T extends DtoT> T convertFrom(D domain) {
        Assert.notNull(domain, "domain must not be null");
        BeanUtils.copyProperties(domain, this);
        return (T) this;
    }

}
