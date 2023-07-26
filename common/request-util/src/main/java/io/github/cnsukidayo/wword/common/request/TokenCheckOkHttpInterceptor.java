package io.github.cnsukidayo.wword.common.request;

import io.github.cnsukidayo.wword.common.request.implement.core.UserRequestUtil;
import io.github.cnsukidayo.wword.model.support.WWordConst;
import io.github.cnsukidayo.wword.model.token.AuthToken;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Optional;

/**
 * token检查的拦截器,如果当前用户拥有access_token,但是access_token的时效已经过期.<br>
 * 则当前拦截器会去自动刷新token,从而做到用户端无感知.
 *
 * @author sukidayo
 * @date 2023/7/23 16:37
 */
public final class TokenCheckOkHttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 获取当前的请求体
        Request request = chain.request();
        // 添加token信息
        AuthToken authToken = RequestRegister.getAuthToken();
        request = request.newBuilder().header(WWordConst.API_ACCESS_KEY_HEADER_NAME, Optional.ofNullable(authToken.getAccessToken()).orElse("")).build();
        Response response = chain.proceed(request);
        if (response.code() == 401) {
            // token过期,尝试刷新token
            UserRequestUtil.refresh();
            // 重新请求当前方法(会再走一次拦截器,重新设置token等内容),拿到返回值进行返回
            return RequestRegister.getRequestHandler().execute(response.request());
        }
        return response;
    }
}
