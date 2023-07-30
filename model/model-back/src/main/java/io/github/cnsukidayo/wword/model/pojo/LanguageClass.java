package io.github.cnsukidayo.wword.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.cnsukidayo.wword.model.pojo.base.BaseEntity;

/**
 * 所有语种类别
 *
 * @author sukidayo
 * @date 2023/7/28 15:04
 */
@TableName("language_class")
public class LanguageClass extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("language")
    private String language;

    @TableField("icon_url")
    private String iconUrl;

    @TableField("order_info")
    private Integer orderInfo;

    public LanguageClass() {
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
