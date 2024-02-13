package io.github.cnsukidayo.wword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.entity.base.BaseEntity;
import io.github.cnsukidayo.wword.model.enums.SystemInfoType;

/**
 * @author sukidayo
 * @date 2024/2/8 19:38
 */
@TableName("system_info")
public class SystemInfo extends BaseEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("info_type")
    private SystemInfoType systemInfoType;

    @TableField("context")
    private String context;

    @TableField("represent")
    private String represent;

    public SystemInfo() {
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
