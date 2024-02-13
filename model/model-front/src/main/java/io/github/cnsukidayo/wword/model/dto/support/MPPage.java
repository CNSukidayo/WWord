package io.github.cnsukidayo.wword.model.dto.support;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.cnsukidayo.wword.model.base.OutputConverter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * mybatis-plus的分页对象
 *
 * @author sukidayo
 * @date 2024/2/12 20:04
 */
@Schema(description = "自已封装的分页查询返回对象")
public class MPPage<T> implements OutputConverter<MPPage<T>, IPage<T>> {

    @Schema(description = "记录列表")
    private List<T> records;

    @Schema(description = "总条数")
    private Long total;

    @Schema(description = "每页显示条数")
    private Long size;

    @Schema(description = "当前页")
    private Long current;

    @Schema(description = "自动优化 COUNT SQL【 默认：true 】")
    private boolean optimizeCountSql;

    @Schema(description = "进行 count 查询 【 默认: true 】")
    private boolean searchCount;

    @Schema(description = "最大每页分页数限制,优先级高于分页插件内的 maxLimit")
    private Long maxLimit;

    @Schema(description = "MappedStatement 的 id")
    private String countId;

    @Schema(description = "当前分页总数")
    private Long pages;

    public MPPage() {
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public boolean isOptimizeCountSql() {
        return optimizeCountSql;
    }

    public void setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public Long getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Long maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getCountId() {
        return countId;
    }

    public void setCountId(String countId) {
        this.countId = countId;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }
}
