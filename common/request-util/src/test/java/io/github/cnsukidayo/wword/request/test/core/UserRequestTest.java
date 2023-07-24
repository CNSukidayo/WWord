package io.github.cnsukidayo.wword.request.test.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.cnsukidayo.wword.common.request.MySSLSocketClient;
import io.github.cnsukidayo.wword.common.request.RequestHandler;
import io.github.cnsukidayo.wword.common.request.RequestHandlerBuilder;
import io.github.cnsukidayo.wword.common.request.RequestRegister;
import io.github.cnsukidayo.wword.dto.UserProfileDTO;
import io.github.cnsukidayo.wword.support.BaseResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.Test;

/**
 * @author sukidayo
 * @date 2023/7/24 9:49
 */
public class UserRequestTest {

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .sslSocketFactory(MySSLSocketClient.getSSLSocketFactory(), MySSLSocketClient.getX509TrustManager())
            .hostnameVerifier(MySSLSocketClient.getHostnameVerifier())
            .build();
    Gson gson = new Gson();
    RequestHandler requestHandler = new RequestHandlerBuilder()
            .gson(gson)
            .okHttpClient(okHttpClient)
            .build();

    @Test
    public void testGetProfile() {
        RequestRegister.register(requestHandler);
        Request request = new Request.Builder()
                .url("http://localhost:8200/api/u/user/getProfile")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ""))
                .build();
        BaseResponse<UserProfileDTO> baseResponse = requestHandler.execute(request, new TypeToken<BaseResponse<UserProfileDTO>>() {
        }.getType());
    }

}
