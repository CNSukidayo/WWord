package io.github.cnsukidayo.wword.model.dto.support;

import io.github.cnsukidayo.wword.model.base.OutputConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 该分页对象只能把SpringData的Page对象转为DataPage对象<br>
 * 如果需要转换类型则自主调用{@link DataPage#convertMap(Function)} ()}方法
 *
 * @author sukidayo
 * @date 2024/2/13 9:55
 */
@Schema(description = "封装SpringData的分页查询对象")
public class DataPage<T> implements OutputConverter<DataPage<T>, Page<T>> {

    @Schema(description = "所有的数据")
    private List<T> content;

    @Schema(description = "是否是最后一页")
    private boolean last;

    @Schema(description = "全部的元素个数")
    private long totalElements;

    @Schema(description = "总页数")
    private int totalPages;

    @Schema(description = "每页的大小")
    private int size;

    @Schema(description = "当前页的索引,从0开始计数")
    private int number;

    @Schema(description = "当前页是否是第一页")
    private boolean first;

    @Schema(description = "本页中查询到的元素个数")
    private int numberOfElements;

    public DataPage() {
    }

    /**
     * DataPage 的泛型转换
     *
     * @param mapper 转换函数
     * @param <R>    转换后的泛型
     * @return 转换泛型后的 IPage
     */
    @SuppressWarnings("unchecked")
    public <R> DataPage<R> convertMap(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getContent().stream().map(mapper).collect(Collectors.toList());
        this.setContent((List<T>) collect);
        return (DataPage<R>) this;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
}
