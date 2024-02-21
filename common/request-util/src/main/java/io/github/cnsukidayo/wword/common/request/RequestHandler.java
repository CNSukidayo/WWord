package io.github.cnsukidayo.wword.common.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Optional;

/**
 * 请求处理器,通过该工具类进行请求的发起和响应<br>
 * 采用代理模式
 *
 * @author sukidayo
 * @date 2023/7/23 16:05
 */
public class RequestHandler {

    private static OkHttpClient okHttpClient;

    private final Gson gson;

    private final OkHttpClientExceptionHandler commonExceptionHandler;

    private HttpUrl baseUrl;

    public static final String APPLICATION_JSON_VALUE = "application/json";

    private Runnable refreshTokenFailHandler;

    public static final String EMPTY_BODY = "";

    public RequestHandler(OkHttpClient okHttpClient,
                          Gson gson,
                          OkHttpClientExceptionHandler commonExceptionHandler) {
        if (gson == null) throw new IllegalArgumentException("gson param must not be null");
        RequestHandler.okHttpClient = okHttpClient;
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
    public static OkHttpClient getOkHttpClient() {
        return RequestHandler.okHttpClient;
    }

    /**
     * 创建带有前缀的url
     *
     * @param path 请求路径
     * @return 返回值不为null
     */
    public RequestTemplate createPrefixUrl(String path) {
        if (this.baseUrl == null) throw new IllegalArgumentException("未设置前缀");
        // todo 格式化前缀
        return new RequestTemplate(path);
    }

    public class RequestTemplate {

        private String[] pathVariables;

        private Map<String, String> requestParams;

        private final String prefixUrl;

        RequestTemplate(String prefixUrl) {
            this.prefixUrl = prefixUrl;
        }

        public RequestTemplate setPathVariable(String... pathVariables) {
            this.pathVariables = pathVariables;
            return this;
        }

        public RequestTemplate setRequestParams(Map<String, String> requestParams) {
            this.requestParams = requestParams;
            return this;
        }

        public HttpUrl build() {
            if (prefixUrl == null || prefixUrl.isEmpty())
                throw new IllegalArgumentException("prefixUrl must not be null");
            StringBuilder path = new StringBuilder(prefixUrl);
            // 末尾拼接分隔符
            if (path.charAt(path.length() - 1) != WWordConst.separatorChar) {
                path.append(WWordConst.separatorChar);
            }
            if (pathVariables != null) {
                for (String pathVariable : pathVariables) {
                    path.append(pathVariable)
                        .append(WWordConst.separatorChar);
                }
            }
            // 删除末尾分隔符
            if (path.charAt(path.length() - 1) == WWordConst.separatorChar) {
                path.deleteCharAt(path.length() - 1);
            }
            if (requestParams != null) {
                path.append("?");
                for (Map.Entry<String, String> requestParam : requestParams.entrySet()) {
                    path.append(requestParam.getKey())
                        .append("=")
                        .append(requestParam.getValue())
                        .append("&");
                }
            }
            return baseUrl.newBuilder(path.toString()).build();
        }

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

    /**
     * 执行
     *
     * @param request         请求对象
     * @param responseWrapper 必须传入使用匿名内部,否则无法获取到泛型
     */
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
