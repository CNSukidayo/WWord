package io.github.cnsukidayo.wword.model.exception;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 统一的异常枚举类,状态码一共四位.<br>
 * 2xxx
 * 3xxx
 * 4xxx
 * 5xxx
 *
 * @author sukidayo
 * @date 2023/8/30 22:48
 */
@Schema(description = "统一的异常枚举类")
public enum ResultCodeEnum {

    ADD_FAIL(4000, "添加失败"),
    REMOVE_FAIL(4001, "删除失败"),
    NOT_EXISTS(4002, "指定的目标不存在"),
    ILLEGAL_STATE(4003, "不合法的状态"),
    ALREADY_EXIST(4004, "指定的目标已经存在"),
    FILE_OPERATION(4005, "文件处理异常"),
    FILE_UPLOAD_ERROR(4006, "文件上传失败"),
    JSON_HANDLE_ERROR(4007, "Json解析异常"),

    /**
     * 如果返回此状态码,前端需要刷新token
     **/
    LOGIN_FAIL(4100, "未登录,请登陆后访问"),
    /**
     * 如果返回此状态码,前端不需要刷新token
     **/
    LOGIN_STATE_INVALID(4101, "登陆状态已失效,请重新登陆"),
    AUTHENTICATION(4102, "您没有权限访问该内容!"),
    STAR_FAIL(4104, "收藏失败!"),
    PASSWORD_INCONSISTENT(4105, "两次输入密码不一致"),
    OLD_PASSWORD_ERROR(4107, "旧密码错误"),
    NO_LOGIN(4108, "您尚未登录,因此无法注销"),
    GET_USER_FAIL(4109, "获取用户失败"),

    UNTRACE_ID_LIST_NOT_EMPTY(4303, "待取消跟踪的接口的id列表不能为空!");

    private final Integer code;

    private final String message;

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
