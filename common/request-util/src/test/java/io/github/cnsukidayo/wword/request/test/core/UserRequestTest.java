package io.github.cnsukidayo.wword.request.test.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.cnsukidayo.wword.common.request.*;
import io.github.cnsukidayo.wword.dto.UserProfileDTO;
import io.github.cnsukidayo.wword.support.BaseResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author sukidayo
 * @date 2023/7/24 9:49
 */
public class UserRequestTest {

    @Test
    public void testGetProfile() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResource("cert/publicKey.cer").openStream();
        SSLSocketFactoryCreate sslSocketFactoryCreate = SSLSocketFactoryCreate.newInstance(inputStream);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .hostnameVerifier(new OkHttpHostnameVerifier())
                .sslSocketFactory(sslSocketFactoryCreate.getSslSocketFactory(),sslSocketFactoryCreate.getX509TrustManager())
                .addInterceptor(new TokenCheckOkHttpInterceptor())
                .build();
        Gson gson = new Gson();
        RequestHandler requestHandler = new RequestHandler(okHttpClient,gson,null);
        Request request = new Request.Builder()
                .url("https://localhost:8200/api/u/user/getProfile")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ""))
                .build();
        RequestRegister.register(requestHandler);
        BaseResponse<UserProfileDTO> baseResponse = requestHandler.execute(request, new TypeToken<BaseResponse<UserProfileDTO>>() {
        }.getType());
    }


}
