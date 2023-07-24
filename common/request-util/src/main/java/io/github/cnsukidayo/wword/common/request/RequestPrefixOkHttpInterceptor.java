package io.github.cnsukidayo.wword.common.request;

import okhttp3.Request;
import okhttp3.Response;

import java.io.File;

/**
 * @author sukidayo
 * @date 2023/7/24 11:25
 */
final class RequestPrefixOkHttpInterceptor implements OkHttpInterceptor {

    private String baseUrl = null;

    /**
     * 设置基本的请求路径(前缀)
     *
     * @param baseUrl 请求的基础url
     */
    public RequestPrefixOkHttpInterceptor(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 设置基本的请求路径(前缀)
     *
     * @param protocol  请求协议
     * @param ipAddress 请求IP地址
     * @param port      请求端口号
     */
    public RequestPrefixOkHttpInterceptor(String protocol, String ipAddress, String port) {
        this("https" + ":" + File.separatorChar + File.separatorChar + ipAddress + ":" + port + File.separatorChar);
    }

    /**
     * 设置基本的请求路径,默认请求协议是https
     *
     * @param ipAddress 请求IP
     * @param port      请求端口
     */
    public RequestPrefixOkHttpInterceptor(String ipAddress, String port) {
        this("https", ipAddress, port);
    }


    @Override
    public int getOrder() {
        return 5;
    }

    @Override
    public void requestHandle(Request request) {

    }

    @Override
    public Response responseHandle(Response response) {
        return response;
    }
}
