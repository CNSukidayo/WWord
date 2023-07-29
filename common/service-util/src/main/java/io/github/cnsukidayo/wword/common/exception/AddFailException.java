package io.github.cnsukidayo.wword.common.exception;

/**
 * @author sukidayo
 * @date 2023/7/29 14:19
 */
public class AddFailException extends BadRequestException {

    public AddFailException(String message) {
        super(message);
    }

    public AddFailException() {
        super("添加失败");
    }

}
