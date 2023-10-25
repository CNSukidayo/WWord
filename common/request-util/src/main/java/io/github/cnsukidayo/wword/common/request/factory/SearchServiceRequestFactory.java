package io.github.cnsukidayo.wword.common.request.factory;

import io.github.cnsukidayo.wword.common.request.implement.search.WordSearchEnum;
import io.github.cnsukidayo.wword.common.request.interfaces.search.WordSearchRequest;

/**
 * 权限模块的请求类单利工厂
 *
 * @author sukidayo
 * @date 2023/10/25 12:56
 */
public class SearchServiceRequestFactory {

    private static class SearchServiceRequestFactoryHandler {
        private static final SearchServiceRequestFactory REQUEST_FACTORY = new SearchServiceRequestFactory();
    }

    private SearchServiceRequestFactory() {
    }

    public static SearchServiceRequestFactory getInstance() {
        return SearchServiceRequestFactoryHandler.REQUEST_FACTORY;
    }

    /**
     * ES单词管理接口
     *
     * @return 返回值不为null
     */
    public WordSearchRequest wordSearchRequest() {
        return WordSearchEnum.DEFAULT_IMPLEMENT;
    }


}
