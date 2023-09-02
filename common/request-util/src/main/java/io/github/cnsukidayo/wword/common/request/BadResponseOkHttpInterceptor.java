package io.github.cnsukidayo.wword.common.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
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

/**
 * 如果响应是非正确的,则需要有相关的错误处理器.
 *
 * @author sukidayo
 * @date 2023/7/23 16:37
 */
public final class BadResponseOkHttpInterceptor implements Interceptor {

    private Gson gson;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public BadResponseOkHttpInterceptor(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 获取当前的请求体
        Request request = chain.request();
        Response response = chain.proceed(request);
        // 如果出现异常,终止操作,下面这段代码会最后执行.
        String body = null;
        try {
            body = getResponseBody(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!response.isSuccessful()) {
            Type type = new TypeToken<ErrorVo>() {
            }.getType();
            ErrorVo errorVo = gson.fromJson(body, type);
            throw new RuntimeException(errorVo.getError());
        }
        // 如果外层没有出现异常并不能代表内层不会出现异常,至少有返回数据了
        Type type = new TypeToken<BaseResponse<ErrorVo>>() {
        }.getType();
        BaseResponse<ErrorVo> result = gson.fromJson(body, type);
        if (!result.getStatus().equals(200)) {
            throw new RuntimeException("error" + result.getData().getError() +
                "message" + result.getData().getMessage());
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
