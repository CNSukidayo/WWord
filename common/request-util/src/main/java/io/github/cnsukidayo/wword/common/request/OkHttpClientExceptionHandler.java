package io.github.cnsukidayo.wword.common.request;

import java.io.IOException;

/**
 * 处理OkHttpClient异常的处理器
 * @author sukidayo
 * @date 2023/7/24 11:16
 */
public interface OkHttpClientExceptionHandler {

    void handlerError(IOException ioException);

}
