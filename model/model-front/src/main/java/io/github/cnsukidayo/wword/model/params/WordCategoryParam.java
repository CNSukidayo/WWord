package io.github.cnsukidayo.wword.model.params;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.WordCategory;
import io.github.cnsukidayo.wword.model.validate.CreateCheck;
import io.github.cnsukidayo.wword.model.validate.UpdateCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

/**
 * @author sukidayo
 * @date 2023/7/27 9:45
 */
@Schema(description = "单词收藏夹请求体参数")
public class WordCategoryParam implements InputConverter<WordCategory> {

    @Schema(description = "收藏夹的id")
    @NotNull(message = "收藏夹id不能为空", groups = UpdateCheck.class)
    private Long id;

    @Schema(description = "收藏夹标题")
    @NotNull(message = "收藏夹标题不能为null", groups = {CreateCheck.class, UpdateCheck.class})
    private String title;

    @Schema(description = "收藏夹描述信息")
    @NotNull(message = "收藏夹描述信息不能为null", groups = {CreateCheck.class, UpdateCheck.class})
    private String describeInfo;

    @Schema(description = "收藏夹的排序")
    @NotNull(message = "收藏夹的排序值未指定!", groups = {UpdateCheck.class})
    @Null(message = "新增收藏夹排序值不允许指定", groups = {CreateCheck.class})
    private Integer categoryOrder;

    public WordCategoryParam() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    public void setDescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo;
    }

    public Integer getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(Integer categoryOrder) {
        this.categoryOrder = categoryOrder;
    }
}
