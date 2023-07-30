package io.github.cnsukidayo.wword.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 可见性的枚举类
 *
 * @author sukidayo
 * @date 2023/7/26 20:19
 */
@Schema(description = "收藏夹的状态枚举,即当前收藏夹是个什么成分.")
public enum CategoryAttribute {
    PUBLIC("公开"),
    PRIVATE("私密"),
    LINK("链接");

    private final String value;

    CategoryAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
