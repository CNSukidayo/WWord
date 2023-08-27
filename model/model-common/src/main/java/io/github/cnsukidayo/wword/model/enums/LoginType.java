package io.github.cnsukidayo.wword.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/8/27 10:45
 */
@Schema(description = "登陆类型的枚举")
public enum LoginType {

    LOGIN_IN("登陆成功"),
    LOGIN_OUT("退出登陆"),
    LOGIN_FAIL("登陆失败"),
    PASSWORD_UPDATE("修改密码");

    private final String value;

    LoginType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
