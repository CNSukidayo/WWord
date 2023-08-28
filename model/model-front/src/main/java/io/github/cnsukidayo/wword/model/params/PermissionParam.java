package io.github.cnsukidayo.wword.model.params;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.Permission;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author sukidayo
 * @date 2023/8/27 19:22
 */
@Schema(description = "权限接口参数")
public class PermissionParam implements InputConverter<Permission> {

    @Schema(description = "模块名称")
    @NotBlank(message = "模块名称不能为空")
    @Size(max = 255, message = "模块名称不能超过 {max} 个字符")
    private String mould;

    @Schema(description = "接口名称")
    @NotBlank(message = "接口名称不为空")
    @Size(max = 255, message = "接口名称不能超过 {max} 个字符")
    private String interfaces;

    // todo 接口的路径匹配是一个问题,需要使用ant组件
    @Schema(description = "接口路径")
    @NotBlank(message = "接口路径不能为空")
    @Size(max = 255, message = "接口路径不能超过 {max} 个字符")
    private String path;

    @Schema(description = "接口名称")
    @NotBlank(message = "接口名称不能为空")
    @Size(max = 255, message = "接口名称不能超过 {max} 个字符")
    private String interfaceDescribe;

    public PermissionParam() {
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

