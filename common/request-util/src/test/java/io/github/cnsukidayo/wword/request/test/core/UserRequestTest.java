package io.github.cnsukidayo.wword.request.test.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.cnsukidayo.wword.common.request.*;
import io.github.cnsukidayo.wword.common.request.type.deser.GLocalDateDeSerializer;
import io.github.cnsukidayo.wword.common.request.type.deser.GLocalDateTimeDeSerializer;
import io.github.cnsukidayo.wword.common.request.type.ser.GLocalDateSerializer;
import io.github.cnsukidayo.wword.common.request.type.ser.GLocalDateTimeSerializer;
import io.github.cnsukidayo.wword.model.dto.UserProfileDTO;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.token.AuthToken;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Consumer;

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
            .addInterceptor(new TokenCheckOkHttpInterceptor(gson))
            .addInterceptor(new BadResponseOkHttpInterceptor(gson))
            .build();

        RequestHandler requestHandler = new RequestHandler(okHttpClient, gson, null);
        requestHandler.setBaseUrl("https://localhost:8201");
        requestHandler.setRefreshTokenFailHandler(() -> {

        });

        RequestRegister.register(requestHandler);

        Request request = new Request.Builder()
            .url(requestHandler.createPrefixUrl("api/u/user/getProfile"))
            .post(requestHandler.jsonBody(null))
            .get()
            .build();
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken("sd12as123");
        RequestRegister.setAuthToken(authToken);
        new ResponseWrapper<BaseResponse<UserProfileDTO>>(requestHandler, request) {
        }.success(new Consumer<BaseResponse<UserProfileDTO>>() {
            @Override
            public void accept(BaseResponse<UserProfileDTO> userProfileDTOBaseResponse) {

            }
        }).fail(new Consumer<BaseResponse<ErrorVo>>() {
            @Override
            public void accept(BaseResponse<ErrorVo> errorVoBaseResponse) {

            }
        }).execute();
        /*
        LoginParam loginParam = new LoginParam();
        loginParam.setAccount("caixukun");
        loginParam.setPassword("123456789");
        UserRequestUtil.login(loginParam).fail(new Consumer<BaseResponse<ErrorVo>>() {
            @Override
            public void accept(BaseResponse<ErrorVo> errorVoBaseResponse) {
                System.out.println(errorVoBaseResponse.getData().getMessage());
            }
        }).execute();
        UserRequestUtil.getProfile().success(new Consumer<BaseResponse<UserProfileDTO>>() {
            @Override
            public void accept(BaseResponse<UserProfileDTO> userProfileDTOBaseResponse) {
                System.out.println("000000000" + userProfileDTOBaseResponse);
            }
        }).fail(new Consumer<BaseResponse<ErrorVo>>() {
            @Override
            public void accept(BaseResponse<ErrorVo> errorVoBaseResponse) {
                System.out.println(errorVoBaseResponse.getData().getMessage());
            }
        }).execute();
         */
    }

    @Test
    public void testType() {
    }


}
