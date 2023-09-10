package io.github.cnsukidayo.wword.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/28 16:13
 */
@Schema(description = "收藏夹的分类类型")
public enum DivideType {

    OFFICIAL("官方"),
    PERSONAL("个人"),
    BASE("总库");
    private final String value;

    DivideType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
