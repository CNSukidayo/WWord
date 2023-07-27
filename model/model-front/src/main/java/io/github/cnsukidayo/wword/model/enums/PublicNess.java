package io.github.cnsukidayo.wword.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 可见性的枚举类
 *
 * @author sukidayo
 * @date 2023/7/26 20:19
 */
@Schema(description = "可见性的枚举")
public enum PublicNess {
    PUBLIC("公开"),
    PRIVATE("私密");

    private final String value;

    PublicNess(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
