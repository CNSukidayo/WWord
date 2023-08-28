package io.github.cnsukidayo.wword.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/8/28 13:07
 */
@Schema(description = "权限接口装填")
public enum PermissionStatus {

    SUCCESS("正常"),
    FAIL("失效");

    private final String value;

    PermissionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
