package io.github.cnsukidayo.wword.request.test;

import com.google.gson.Gson;
import io.github.cnsukidayo.wword.common.request.OkHttpHostnameVerifier;
import io.github.cnsukidayo.wword.common.request.SSLSocketFactoryCreate;
import io.github.cnsukidayo.wword.common.request.TokenCheckOkHttpInterceptor;
import okhttp3.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author sukidayo
 * @date 2023/7/23 18:54
 */
public class RequestHandlerTest {

    @Test
    public void requestTest() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResource("cert/publicKey.cer").openStream();
        SSLSocketFactoryCreate sslSocketFactoryCreate = SSLSocketFactoryCreate.newInstance(inputStream);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .hostnameVerifier(new OkHttpHostnameVerifier())
                .sslSocketFactory(sslSocketFactoryCreate.getSslSocketFactory(),sslSocketFactoryCreate.getX509TrustManager())
                .addInterceptor(new TokenCheckOkHttpInterceptor())
                .build();
        Gson gson = new Gson();
        Request request = new Request.Builder()
                .url("https://localhost:8200/api/u/categories/ping")
                .post(RequestBody.create(MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE),""))
                .build();
        Response response = okHttpClient.newCall(request).execute();
        System.out.println(response.body().string());
    }

}
