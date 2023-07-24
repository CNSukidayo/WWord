package io.github.cnsukidayo.wword.common.request;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 请求处理器,通过该工具类进行请求的发起和响应<br>
 * 采用代理模式
 *
 * @author sukidayo
 * @date 2023/7/23 16:05
 */
public class RequestHandler {

    private final OkHttpClient okHttpClient;

    private final List<OkHttpInterceptor> okHttpInterceptorList;

    private final Gson gson;

    private final OkHttpClientExceptionHandler okHttpClientExceptionHandler;

    RequestHandler(OkHttpClient okHttpClient,
                   List<OkHttpInterceptor> okHttpInterceptorList,
                   Gson gson,
                   OkHttpClientExceptionHandler okHttpClientExceptionHandler) {
        this.okHttpClient = okHttpClient;
        this.okHttpInterceptorList = okHttpInterceptorList;
        this.gson = gson;
        this.okHttpClientExceptionHandler = okHttpClientExceptionHandler;
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
        okHttpInterceptorList.forEach(okHttpInterceptor -> okHttpInterceptor.requestHandle(request));
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            // 调用默认的处理器进行处理
            if (okHttpClientExceptionHandler != null) {
                okHttpClientExceptionHandler.handlerError(e);
            } else {
                throw new RuntimeException(e);
            }
        }
        for (OkHttpInterceptor okHttpInterceptor : okHttpInterceptorList) {
            response = okHttpInterceptor.responseHandle(response);
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
