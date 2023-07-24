package io.github.cnsukidayo.wword.enums;

/**
 * @author sukidayo
 * @date 2023/7/24 19:27
 */
public enum SexType {

    MALE("男"),
    FEMALE("女"),
    SECRECY("保密");

    private String describe;

    SexType(String describe) {
        this.describe = describe;
    }
}
