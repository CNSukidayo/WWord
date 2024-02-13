package io.github.cnsukidayo.wword.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.cnsukidayo.wword.model.base.OutputConverter;
import io.github.cnsukidayo.wword.model.entity.SystemInfo;
import io.github.cnsukidayo.wword.model.enums.SystemInfoType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * @author sukidayo
 * @date 2024/2/9 15:49
 */
@Schema(description = "系统信息")
public class SystemInfoDTO implements OutputConverter<SystemInfoDTO, SystemInfo> {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "信息的类型")
    private SystemInfoType infoType;

    @Schema(description = "信息的上下文")
    private String context;

    @Schema(description = "信息的描述")
    private String represent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建的时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新的时间")
    private LocalDateTime updateTime;


    public SystemInfoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SystemInfoType getInfoType() {
        return infoType;
    }

    public void setInfoType(SystemInfoType infoType) {
        this.infoType = infoType;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
