package io.github.cnsukidayo.wword.model.params;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.SystemInfo;
import io.github.cnsukidayo.wword.model.enums.SystemInfoType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author sukidayo
 * @date 2024/2/9 16:01
 */
@Schema(description = "系统信息请求参数")
public class SystemInfoParam implements InputConverter<SystemInfo> {

    @Schema(description = "系统信息的id")
    private Long id;

    @Schema(description = "系统信息的类型")
    @NotNull(message = "系统信息类型不能为null")
    private SystemInfoType systemInfoType;

    @Schema(description = "系统信息内容")
    @NotEmpty(message = "系统信息上下文不能为空")
    private String context;

    @Schema(description = "系统信息描述信息")
    @NotEmpty(message = "系统信息描述信息不能为空")
    private String represent;

    public SystemInfoParam() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SystemInfoType getSystemInfoType() {
        return systemInfoType;
    }

    public void setSystemInfoType(SystemInfoType systemInfoType) {
        this.systemInfoType = systemInfoType;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getRepresent() {
        return represent;
    }

    public void setRepresent(String represent) {
        this.represent = represent;
    }
}
