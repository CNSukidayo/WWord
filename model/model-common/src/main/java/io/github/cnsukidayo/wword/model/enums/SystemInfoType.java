package io.github.cnsukidayo.wword.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2024/2/8 19:23
 */
@Schema(description = "系统信息类别的枚举")
public enum SystemInfoType {

    USER_POLICY,
    PRIVATE_POLICY;

    SystemInfoType() {
    }

}
