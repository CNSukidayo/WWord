package io.github.cnsukidayo.wword.request.test.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.cnsukidayo.wword.common.request.*;
import io.github.cnsukidayo.wword.common.request.type.deser.GLocalDateDeSerializer;
import io.github.cnsukidayo.wword.common.request.type.deser.GLocalDateTimeDeSerializer;
import io.github.cnsukidayo.wword.common.request.type.ser.GLocalDateSerializer;
import io.github.cnsukidayo.wword.common.request.type.ser.GLocalDateTimeSerializer;
import io.github.cnsukidayo.wword.model.dto.UserProfileDTO;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.token.AuthToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author sukidayo
 * @date 2023/7/24 9:49
 */
public class UserRequestTest {

    @Test
    public void testGetProfile() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResource("cert/publicKey.cer").openStream();
        SSLSocketFactoryCreate sslSocketFactoryCreate = SSLSocketFactoryCreate.newInstance(inputStream);
        Gson gson = new GsonBuilder()
            //LocalDateTime序列化适配器
            .registerTypeAdapter(LocalDateTime.class, new GLocalDateTimeSerializer())
            //LocalDate序列化适配器
            .registerTypeAdapter(LocalDate.class, new GLocalDateSerializer())
            //LocalDateTime反序列化适配器
            .registerTypeAdapter(LocalDateTime.class, new GLocalDateTimeDeSerializer())
            //LocalDate反序列化适配器
            .registerTypeAdapter(LocalDate.class, new GLocalDateDeSerializer())
            .create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .hostnameVerifier(new OkHttpHostnameVerifier())
            .sslSocketFactory(sslSocketFactoryCreate.getSslSocketFactory(), sslSocketFactoryCreate.getX509TrustManager())
            .addInterceptor(new BadResponseOkHttpInterceptor(gson))
            .addInterceptor(new TokenCheckOkHttpInterceptor(gson))
            .build();

        RequestHandler requestHandler = new RequestHandler(okHttpClient, gson, null);
        requestHandler.setBaseUrl("https://localhost:8201");
        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/u/user/getProfile"))
//            .post(requestHandler.jsonBody(null))
            .get()
            .build();
        RequestRegister.register(requestHandler);
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken("bcd64a59ba5147ce9ed4f734bc4669eb");
        RequestRegister.setAuthToken(authToken);
        BaseResponse<Object> baseResponse = requestHandler.execute(request, new TypeToken<BaseResponse<UserProfileDTO>>() {
        }.getType());
        System.out.println(baseResponse);
    }


}
