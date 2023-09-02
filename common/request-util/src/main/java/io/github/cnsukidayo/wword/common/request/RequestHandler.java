package io.github.cnsukidayo.wword.common.request;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * 请求处理器,通过该工具类进行请求的发起和响应<br>
 * 采用代理模式
 *
 * @author sukidayo
 * @date 2023/7/23 16:05
 */
public class RequestHandler {

    private final OkHttpClient okHttpClient;

    private final Gson gson;

    private final OkHttpClientExceptionHandler commonExceptionHandler;

    private HttpUrl baseUrl;

    public RequestHandler(OkHttpClient okHttpClient,
                          Gson gson,
                          OkHttpClientExceptionHandler commonExceptionHandler) {
        if (gson == null) throw new IllegalArgumentException("gson param must not be null");
        this.okHttpClient = okHttpClient;
        this.gson = gson;
        this.commonExceptionHandler = commonExceptionHandler;
    }


    public HttpUrl getBaseUrl() {
        return baseUrl;
    }

    /**
     * 设置基本的请求路径(前缀)
     *
     * @param baseUrl 请求的基础url
     */
    public void setBaseUrl(HttpUrl baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 设置基本的请求路径(前缀)
     *
     * @param baseUrl 请求的基础url
     */
    public void setBaseUrl(String baseUrl) {
        this.setBaseUrl(HttpUrl.get(baseUrl));
    }

    /**
     * 设置基本的请求路径(前缀)
     *
     * @param protocol 请求协议
     * @param host     请求IP地址
     * @param port     请求端口号
     */
    public void setBaseUrl(String protocol, String host, Integer port) {
        this.setBaseUrl(new HttpUrl.Builder().scheme(protocol).host(host).port(port).build());
    }

    /**
     * 设置基本的请求路径,默认请求协议是https
     *
     * @param host 请求IP
     * @param port 请求端口
     */
    public void setBaseUrl(String host, Integer port) {
        this.setBaseUrl(new HttpUrl.Builder().scheme("https").host(host).port(port).build());
    }


    /**
     * 得到原生的OkHttpClient对象
     *
     * @return 返回创建当前对象的OkHttpClient
     */
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * 创建带有前缀的url
     *
     * @param path 请求路径
     * @return 返回值不为null
     */
    public HttpUrl createPrefixUrl(String path) {
        if (this.baseUrl == null) throw new IllegalArgumentException("未设置前缀");
        return baseUrl.newBuilder(path).build();
    }

    /**
     * 创建json格式的请求体
     *
     * @param json 请求体,json格式的字符串形式
     * @return 返回值不为null
     */
    public RequestBody jsonBody(String json) {
        return RequestBody.create(MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE), Optional.ofNullable(json).orElse(""));
    }

    /**
     * 创建json格式的请求体
     *
     * @param jsonObject 请求体对象,会自动序列化
     * @return 返回值不为null
     */
    public <T> RequestBody jsonBody(T jsonObject) {
        String json = gson.toJson(jsonObject);
        return RequestBody.create(MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE), json);
    }

    public Response execute(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            // 调用默认的处理器进行处理
            if (commonExceptionHandler != null) {
                commonExceptionHandler.handlerError(e);
            } else {
                throw new RuntimeException(e);
            }
        }
        return response;
    }

    public <T> T execute(Request request, Type type) {
        Response response = execute(request);

        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return null;
        }
        String body = null;
        try {
            body = responseBody.string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(body);
        return gson.fromJson(body, type);
    }

}
