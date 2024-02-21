package io.github.cnsukidayo.wword.common.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.cnsukidayo.wword.common.request.factory.AuthServiceRequestFactory;
import io.github.cnsukidayo.wword.common.request.interfaces.auth.UserRequest;
import io.github.cnsukidayo.wword.model.environment.WWordConst;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.token.AuthToken;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Optional;

/**
 * token检查的拦截器,如果当前用户拥有access_token,但是access_token的时效已经过期.<br>
 * 则当前拦截器会去自动刷新token,从而做到用户端无感知.
 *
 * @author sukidayo
 * @date 2023/7/23 16:37
 */
public final class TokenCheckOkHttpInterceptor implements Interceptor {

    private final Gson gson;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 获取单利的UserRequest
    private final UserRequest userRequest = AuthServiceRequestFactory.getInstance().userRequest();

    public TokenCheckOkHttpInterceptor(Gson gson) {
        this.gson = gson;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        // 获取当前的请求体
        Request request = chain.request();
        // 添加token信息
        AuthToken authToken = RequestRegister.getAuthToken();
        request = request.newBuilder().header(WWordConst.API_ACCESS_KEY_HEADER_NAME, Optional.ofNullable(authToken.getAccessToken()).orElse("")).build();
        Response response = chain.proceed(request);
        // 如果请求成功则有数据
        String body;
        ResponseBody responseBody = response.body();
        try {
            body = getResponseBody(responseBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!responseBody.contentType().equals(RequestHandler.APPLICATION_JSON_VALUE)) {
            return response;
        }
        BaseResponse baseResponse = gson.fromJson(body, BaseResponse.class);
        // 首先判断有没有异常
        if (!baseResponse.getStatus().equals(200)) {
            // 如果有异常则将其转为ErrorVO,判断status的值是多少
            Type type = new TypeToken<BaseResponse<ErrorVo>>() {
            }.getType();
            BaseResponse<ErrorVo> errorVoBaseResponse = gson.fromJson(body, type);
            // 如果产生错误则判断是不是token过期了
            if (errorVoBaseResponse.getData().getStatus().equals(ResultCodeEnum.LOGIN_FAIL.getCode())) {
                // token过期,尝试刷新token.如果刷新token失败了,必须由refresh方法来保证失败逻辑
                userRequest.refresh(authToken.getRefreshToken()).execute();
                // 重新请求当前方法(会再走一次拦截器,重新设置token等内容),拿到返回值进行返回
                return RequestRegister.getRequestHandler().execute(response.request());
            }
        }
        return response;
    }

    private String getResponseBody(ResponseBody responseBody) throws Exception {
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        Charset charset = StandardCharsets.UTF_8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(StandardCharsets.UTF_8);
            } catch (UnsupportedCharsetException e) {
                logger.error("将http数据写入流异常,异常原因：{}", ExceptionUtils.getStackTrace(e));
            }
        }

        if (responseBody.contentLength() != 0) {
            return buffer.clone().readString(charset);
        }
        return null;
    }

}
