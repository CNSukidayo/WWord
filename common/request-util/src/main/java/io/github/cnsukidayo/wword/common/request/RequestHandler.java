package io.github.cnsukidayo.wword.common.request;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;

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

    public RequestHandler(OkHttpClient okHttpClient,
                          Gson gson,
                          OkHttpClientExceptionHandler commonExceptionHandler) {
        if (gson == null) throw new IllegalArgumentException("gson param must not be null");
        this.okHttpClient = okHttpClient;
        this.gson = gson;
        this.commonExceptionHandler = commonExceptionHandler;
    }

    /**
     * 得到原生的OkHttpClient对象
     *
     * @return 返回创建当前对象的OkHttpClient
     */
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
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
        return gson.fromJson(body, type);
    }

}
