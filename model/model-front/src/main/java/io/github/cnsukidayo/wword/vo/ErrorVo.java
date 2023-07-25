package io.github.cnsukidayo.wword.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/23 13:33
 */
@Schema(description = "异常信息")
public class ErrorVo {

    @Schema(description = "时间戳")
    private Long timestamp;

    @Schema(description = "状态码")
    private Integer status;

    @Schema(description = "http错误信息")
    private String message;

    @Schema(description = "本次请求的路径")
    private String path;

    @Schema(description = "错误信息")
    private String error;

    @Schema(description = "错误堆栈信息")
    private String trace;

    public ErrorVo() {
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
}
