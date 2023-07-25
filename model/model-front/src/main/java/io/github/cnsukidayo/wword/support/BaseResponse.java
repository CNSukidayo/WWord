package io.github.cnsukidayo.wword.support;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Global response entity.
 *
 * @author cnsukidayo
 */

@Schema(name = "BaseResponse", description = "全局统一返回对象")
public class BaseResponse<T> {

    @Schema(description = "返回状态码")
    private Integer status;

    @Schema(description = "响应信息")
    private String message;

    @Schema(description = "响应数据", name = "Object", type = "Object")
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * Creates an ok result with message and data. (Default status is 200)
     *
     * @param data    result data
     * @param message result message
     * @return ok result with message and data
     */
    @NonNull
    public static <T> BaseResponse<T> ok(@Nullable String message, @Nullable T data) {
        return new BaseResponse<>(HttpStatus.OK.value(), message, data);
    }

    /**
     * Creates an ok result with data only. (Default message is OK, status is 200)
     *
     * @param data data to response
     * @param <T>  data type
     * @return base response with data
     */
    public static <T> BaseResponse<T> ok(@Nullable T data) {
        return new BaseResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * 构造一个通用的返回对象<br>
     * 设置数据,返回对象的方法
     *
     * @param data    the data of response
     * @param status  the status of response
     * @param message the message of response
     * @param <T>     泛型
     * @return 返回值不为null
     */
    public static <T> BaseResponse<T> build(Integer status, String message, T data) {
        //创建Result对象，设置值，返回对象
        BaseResponse<T> commonResult = new BaseResponse<>();
        //判断返回结果中是否需要数据
        if (data != null) {
            //设置数据到result对象
            commonResult.setData(data);
        }
        //设置其他值
        commonResult.setStatus(status);
        commonResult.setMessage(message);
        //返回设置值之后的对象
        return commonResult;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
