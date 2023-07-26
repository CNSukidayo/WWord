package io.github.cnsukidayo.wword.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/24 19:27
 */
@Schema(description = "性别的枚举")
public enum SexType {

    MALE("男"),
    FEMALE("女"),
    SECRECY("保密");

    private final String value;

    SexType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
