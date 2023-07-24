package io.github.cnsukidayo.wword.request.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.cnsukidayo.wword.common.request.MySSLSocketClient;
import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestHandlerBuilder;
import io.github.cnsukidayo.wword.support.BaseResponse;
import io.github.cnsukidayo.wword.vo.ErrorVo;
import okhttp3.*;
import org.junit.Test;

/**
 * @author sukidayo
 * @date 2023/7/23 18:54
 */
public class RequestHandlerTest {

    @Test
    public void requestTest() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(MySSLSocketClient.getSSLSocketFactory(), MySSLSocketClient.getX509TrustManager())
                .hostnameVerifier(MySSLSocketClient.getHostnameVerifier())
                .build();
        Gson gson = new Gson();
        RequestHandler requestHandler = new RequestHandlerBuilder()
                .gson(gson)
                .okHttpClient(okHttpClient)
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8200/getList")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ""))
                .build();
        BaseResponse<ErrorVo> baseResponse = requestHandler.execute(request,new TypeToken<BaseResponse<ErrorVo>>() {
        }.getType());

    }

}
