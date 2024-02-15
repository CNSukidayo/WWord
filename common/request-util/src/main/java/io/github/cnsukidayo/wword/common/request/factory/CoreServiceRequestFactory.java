package io.github.cnsukidayo.wword.common.request.factory;

import io.github.cnsukidayo.wword.common.request.implement.core.*;
import io.github.cnsukidayo.wword.common.request.interfaces.core.*;

/**
 * 权限模块的请求类单利工厂
 *
 * @author sukidayo
 * @date 2023/10/25 12:56
 */
public class CoreServiceRequestFactory {

    private static class CoreServiceRequestFactoryHandler {
        private static final CoreServiceRequestFactory REQUEST_FACTORY = new CoreServiceRequestFactory();
    }

    private CoreServiceRequestFactory() {
    }

    public static CoreServiceRequestFactory getInstance() {
        return CoreServiceRequestFactoryHandler.REQUEST_FACTORY;
    }

    /**
     * 用户帖子收藏管理接口
     *
     * @return 返回值不为null
     */
    public PostCategoryRequest postCategoryRequest() {
        return PostCategoryRequestEnum.DEFAULT_IMPLEMENT;
    }

    /**
     * 学校管理接口
     *
     * @return 返回值不为null
     */
    public UniversityRequest universityRequest() {
        return UniversityRequestEnum.DEFAULT_IMPLEMENT;
    }

    /**
     * 划分列表管理接口
     *
     * @return 返回值不为null
     */
    public DivideRequest divideRequest() {
        return DivideRequestEnum.DEFAULT_IMPLEMENT;
    }


    /**
     * 单词结构管理接口
     *
     * @return 返回值不为null
     */
    public WordStructureRequest wordStructureRequest() {
        return WordStructureRequestEnum.DEFAULT_IMPLEMENT;
    }

    /**
     * 系统信息管理接口
     *
     * @return 返回值不为null
     */
    public SystemInfoRequest systemInfoRequest() {
        return SystemInfoRequestEnum.DEFAULT_IMPLEMENT;
    }

    /**
     * 单词管理接口
     *
     * @return 返回值不为null
     */
    public WordRequest wordRequest() {
        return WordRequestEnum.DEFAULT_IMPLEMENT;
    }

}
