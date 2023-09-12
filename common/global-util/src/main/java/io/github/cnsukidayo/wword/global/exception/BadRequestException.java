package io.github.cnsukidayo.wword.global.exception;

import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;

public class BadRequestException extends AbstractWWordException {

    private final Integer status;

    public BadRequestException(ResultCodeEnum resultCodeEnum) {
        this(resultCodeEnum.getCode(), resultCodeEnum.getMessage());
    }

    public BadRequestException(ResultCodeEnum resultCodeEnum, String message) {
        this(resultCodeEnum.getCode(), message);
    }

    public BadRequestException(ResultCodeEnum resultCodeEnum, Throwable cause) {
        super(resultCodeEnum.getMessage(), cause);
        this.status = resultCodeEnum.getCode();
    }

    public BadRequestException(Integer status, String message) {
        super(message);
        this.status = status;
    }

    @Override
    public Integer getStatus() {
        return status;
    }
}
