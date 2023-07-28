package io.github.cnsukidayo.wword.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/28 15:06
 */
@Schema(description = "语种分类对象,用于展示当前平台支持的语种类别")
public class LanguageClassDTO {

    @Schema(description = "语种的id")
    private Long id;

    @Schema(description = "语种的名称")
    private String language;

    @Schema(description = "语种图标")
    private String iconUrl;

    @Schema(description = "展示的顺序")
    private Integer orderInfo;

    public LanguageClassDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
