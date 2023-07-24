package io.github.cnsukidayo.wword.common.request;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器
 *
 * @author sukidayo
 * @date 2023/7/23 16:37
 */
public interface OkHttpInterceptor {
    /**
     * 请求拦截器的顺序,值越高的会优先执行.该接口是一个回调接口
     *
     * @return 返回当前拦截器的排序
     */
    int getOrder();


    /**
     * 在真正执行请求前的那一瞬间会调用该方法.
     *
     * @param request 请求
     */
    void requestHandle(Request request);

    /**
     * 响应处理,在请求结束之后会调用该方法<br>
     * 回调方法
     *
     * @param response 响应
     * @return 修改后的response
     */
    Response responseHandle(Response response);

}
