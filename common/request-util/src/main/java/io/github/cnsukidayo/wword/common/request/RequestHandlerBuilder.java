package io.github.cnsukidayo.wword.common.request;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 包括请求的ip地址、请求的端口、拦截器、线程池等都在这里进行设置.<br>
 * 这里只是在HttpClient的基础上封装了一层,本质的特性和HttpClient还是相同的.
 *
 * @author sukidayo
 * @date 2023/7/23 16:05
 */
public class RequestHandlerBuilder {

    private final List<OkHttpInterceptor> okHttpInterceptorList = new ArrayList<>();

    private OkHttpClient okHttpClient;

    private String baseUrl;

    private Gson gson;

    private OkHttpClientExceptionHandler okHttpClientExceptionHandler;


    public RequestHandlerBuilder() {
        this.addRequestInterceptor(new TokenCheckOkHttpInterceptor());
    }


    /**
     * 设置基本的请求路径(前缀)
     *
     * @param baseUrl 请求的基础url
     * @return 返回当前建造者实例
     */
    public RequestHandlerBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    /**
     * 添加拦截器
     *
     * @param ohHttpInterceptors 拦截器
     * @return 返回当前建造者实例
     */
    public RequestHandlerBuilder addRequestInterceptor(OkHttpInterceptor... ohHttpInterceptors) {
        this.okHttpInterceptorList.addAll(Arrays.asList(ohHttpInterceptors));
        return this;
    }

    /**
     * 设置拦截器
     *
     * @param ohHttpInterceptors 拦截器
     * @return 返回当前建造者实例
     */
    public RequestHandlerBuilder setRequestInterceptor(OkHttpInterceptor... ohHttpInterceptors) {
        this.okHttpInterceptorList.clear();
        return addRequestInterceptor(ohHttpInterceptors);
    }

    /**
     * 设置OkHttpClient,因为只是封装所以本方法并不负责OkHttpClient的创建
     *
     * @param okHttpClient okHttpClient
     * @return 返回当前建造者实例
     */
    public RequestHandlerBuilder okHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return this;
    }

    /**
     * 设置gson用于序列化和反序列化
     *
     * @param gson json解析器
     * @return 返回当前建造者实例
     */
    public RequestHandlerBuilder gson(Gson gson) {
        this.gson = gson;
        return this;
    }

    public RequestHandlerBuilder okHttpClientExceptionHandler(OkHttpClientExceptionHandler okHttpClientExceptionHandler) {
        this.okHttpClientExceptionHandler = okHttpClientExceptionHandler;
        return this;
    }


    /**
     * 构建请求处理器<br>
     * 该请求处理器必须通过调用{@link RequestRegister#register(RequestHandler)}方法进行注册.
     *
     * @return 返回 {@link RequestHandler}实例
     */
    public RequestHandler build() {
        okHttpInterceptorList.sort((o1, o2) -> o1.getOrder() - o2.getOrder());
        if (gson == null) gson = new Gson();
        if (baseUrl != null) this.addRequestInterceptor(new RequestPrefixOkHttpInterceptor(baseUrl));
        return new RequestHandler(okHttpClient, okHttpInterceptorList, gson, okHttpClientExceptionHandler);
    }

}
