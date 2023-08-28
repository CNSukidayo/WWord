package io.github.cnsukidayo.wword.model.dto;

import io.github.cnsukidayo.wword.model.base.OutputConverter;
import io.github.cnsukidayo.wword.model.entity.Permission;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/8/28 12:56
 */
@Schema(description = "接口权限对象,用于展示权限接口的情况")
public class PermissionDTO implements OutputConverter<PermissionDTO, Permission> {

    @Schema(description = "权限接口对象的id")
    private Long id;

    @Schema(description = "模块名称")
    private String mould;

    @Schema(description = "接口名称")
    private String interfaces;

    @Schema(description = "接口路径")
    private String path;

    @Schema(description = "接口描述信息")
    private String interfaceDescribe;

    public PermissionDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMould() {
        return mould;
    }

    public void setMould(String mould) {
        this.mould = mould;
    }

    public String getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String interfaces) {
        this.interfaces = interfaces;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getInterfaceDescribe() {
        return interfaceDescribe;
    }

    public void setInterfaceDescribe(String interfaceDescribe) {
        this.interfaceDescribe = interfaceDescribe;
    }
}
