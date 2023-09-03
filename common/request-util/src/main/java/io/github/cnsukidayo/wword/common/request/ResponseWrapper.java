package io.github.cnsukidayo.wword.common.request;

import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import okhttp3.Request;

import java.util.function.Consumer;

/**
 * @author sukidayo
 * @date 2023/9/3 14:17
 */
public class ResponseWrapper<T extends BaseResponse<?>> {

    private Consumer<T> successConsumer;

    private Consumer<BaseResponse<ErrorVo>> failConsumer;

    private final RequestHandler requestHandler;

    private final Request request;

    public ResponseWrapper(RequestHandler requestHandler, Request request) {
        this.requestHandler = requestHandler;
        this.request = request;
    }

    Consumer<T> getSuccessConsumer() {
        return successConsumer;
    }

    public final ResponseWrapper<T> success(Consumer<T> successConsumer) {
        this.successConsumer = successConsumer;
        return this;
    }

    Consumer<BaseResponse<ErrorVo>> getFailConsumer() {
        return failConsumer;
    }

    public final ResponseWrapper<T> fail(Consumer<BaseResponse<ErrorVo>> failConsumer) {
        this.failConsumer = failConsumer;
        return this;
    }

    public final void execute() {
        requestHandler.execute(request, this);
    }

}
