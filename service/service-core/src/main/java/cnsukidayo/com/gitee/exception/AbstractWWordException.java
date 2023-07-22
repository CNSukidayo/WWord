package cnsukidayo.com.gitee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 项目基本的异常
 *
 * @author cnsukidayo
 * @date 2023/5/19 18:11
 */
public abstract class AbstractWWordException extends RuntimeException {

    /**
     * 错误数据
     */
    private Object errorData;

    public AbstractWWordException(String message) {
        super(message);
    }

    public AbstractWWordException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Http 状态码
     *
     * @return {@link HttpStatus}
     */
    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    /**
     * 设置错误数据
     *
     * @param errorData 错误数据
     * @return 返回当前的异常
     */
    @NonNull
    public AbstractWWordException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }
}
