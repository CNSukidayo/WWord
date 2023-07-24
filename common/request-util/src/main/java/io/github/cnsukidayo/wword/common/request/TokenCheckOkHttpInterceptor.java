package io.github.cnsukidayo.wword.common.request;

import io.github.cnsukidayo.wword.common.request.implement.core.UserRequestUtil;
import okhttp3.Request;
import okhttp3.Response;

/**
 * token检查的拦截器,如果当前用户拥有access_token,但是access_token的时效已经过期.<br>
 * 则当前拦截器会去自动刷新token,从而做到用户端无感知.
 *
 * @author sukidayo
 * @date 2023/7/23 16:37
 */
final class TokenCheckOkHttpInterceptor implements OkHttpInterceptor {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void requestHandle(Request request) {

    }

    @Override
    public Response responseHandle(Response response) {
        if (response.code() == 401) {
            // token过期,尝试刷新token
            Response refreshResponse = UserRequestUtil.refresh();
            // 如果刷新失败则发出异常,终止操作
            if (!refreshResponse.isSuccessful()) {
                throw new RuntimeException("token过期,请重新登陆.");
            }
            // 重新请求当前方法(会再走一次拦截器,重新设置token等内容),拿到返回值进行返回
            return RequestRegister.getRequestHandler().execute(response.request());
        }
        return response;
    }
}
