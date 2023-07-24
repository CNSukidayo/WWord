package io.github.cnsukidayo.wword.enums;


public enum ResultCodeEnum {

    REFRESH_FAIL(220, "刷新token失败");

    private Integer code;

    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}