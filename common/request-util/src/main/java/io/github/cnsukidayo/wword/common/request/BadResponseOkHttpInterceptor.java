package io.github.cnsukidayo.wword.common.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 如果响应是非正确的,则需要有相关的错误处理器.
 *
 * @author sukidayo
 * @date 2023/7/23 16:37
 */
public final class BadResponseOkHttpInterceptor implements Interceptor {

    private Gson gson;

    public BadResponseOkHttpInterceptor(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 获取当前的请求体
        Request request = chain.request();
        Response response = chain.proceed(request);
        // 如果出现异常,终止操作,下面这段代码会最后执行
        if (!response.isSuccessful()) {
            String body = response.body().string();
            Type type = new TypeToken<BaseResponse<ErrorVo>>() {
            }.getType();
            BaseResponse<ErrorVo> baseResponse = gson.fromJson(body, type);
            ErrorVo errorVo = baseResponse.getData();
            throw new RuntimeException(errorVo.getError());
        }
        return response;
    }
}
