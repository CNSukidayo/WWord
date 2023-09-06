package io.github.cnsukidayo.wword.common.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
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

    private final String APPLICATION_JSON_VALUE = "application/json";

    private Runnable refreshTokenFailHandler;

    public RequestHandler(OkHttpClient okHttpClient,
                          Gson gson,
                          OkHttpClientExceptionHandler commonExceptionHandler) {
        if (gson == null) throw new IllegalArgumentException("gson param must not be null");
        this.okHttpClient = okHttpClient;
        this.gson = gson;
        this.commonExceptionHandler = commonExceptionHandler;
        this.refreshTokenFailHandler = refreshTokenFailHandler;
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
        return RequestBody.create(MediaType.parse(APPLICATION_JSON_VALUE), Optional.ofNullable(json).orElse(""));
    }

    /**
     * 设置刷新token失败后的回调函数
     *
     * @param refreshTokenFailHandler 回调函数逻辑不为null
     */
    public void setRefreshTokenFailHandler(Runnable refreshTokenFailHandler) {
        this.refreshTokenFailHandler = refreshTokenFailHandler;
    }

    /**
     * token刷新失败之后的处理逻辑
     *
     * @return 返回回调函数
     */
    public void refreshTokenFailHandler() {
        if (this.refreshTokenFailHandler != null) {
            this.refreshTokenFailHandler.run();
        }
    }

    /**
     * 创建json格式的请求体
     *
     * @param jsonObject 请求体对象,会自动序列化
     * @return 返回值不为null
     */
    public <T> RequestBody jsonBody(T jsonObject) {
        String json = gson.toJson(jsonObject);
        return RequestBody.create(MediaType.parse(APPLICATION_JSON_VALUE), json);
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

    public void execute(Request request, ResponseWrapper<? extends BaseResponse<?>> responseWrapper) {
        Response response = execute(request);
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            throw new IllegalStateException("responseBody is null");
        }
        String body = null;
        try {
            body = responseBody.string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 执行完这一步拿到数据,判断成功还是失败
        BaseResponse baseResponse = gson.fromJson(body, BaseResponse.class);
        // 首先判断有没有异常
        if (baseResponse.getStatus().equals(200)) {
            if (responseWrapper.getSuccessConsumer() != null) {
                responseWrapper.getSuccessConsumer().accept(gson.fromJson(body,
                    ((ParameterizedType) responseWrapper.getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
            }
        } else {
            BaseResponse<ErrorVo> errorVo = gson.fromJson(body, new TypeToken<>() {

            });
            if (responseWrapper.getFailConsumer() != null) {
                responseWrapper.getFailConsumer().accept(errorVo);
            }
        }
    }


}
