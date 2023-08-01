package io.github.cnsukidayo.wword.model.params;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.LanguageClass;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author sukidayo
 * @date 2023/7/29 20:12
 */
@Schema(description = "添加语种请求参数")
public class AddLanguageClassParam implements InputConverter<LanguageClass> {

    @Schema(description = "语种名称")
    @NotEmpty(message = "语言名称不为空")
    private String language;

    @Schema(description = "语种图标的url地址")
    @NotEmpty(message = "语言图标不为空")
    private String iconUrl;

    @Schema(description = "展示的顺序")
    private Integer orderInfo;

    public AddLanguageClassParam() {
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(Integer orderInfo) {
        this.orderInfo = orderInfo;
    }
}
