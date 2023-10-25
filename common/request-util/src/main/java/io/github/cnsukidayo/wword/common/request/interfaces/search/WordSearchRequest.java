package io.github.cnsukidayo.wword.common.request.interfaces.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.entity.es.WordES;
import io.github.cnsukidayo.wword.model.params.SearchWordParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;

/**
 * @author sukidayo
 * @date 2023/10/25 16:41
 */
public interface WordSearchRequest {

    /**
     * 模糊查询单词,这个方法只提供单词的基本信息
     *
     * @param searchWordParam 搜索单词参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Page<WordES>>> searchWord(SearchWordParam searchWordParam);


}
