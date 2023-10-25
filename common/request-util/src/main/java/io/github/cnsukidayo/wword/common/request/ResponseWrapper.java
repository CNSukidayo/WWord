package io.github.cnsukidayo.wword.common.request;

import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.ErrorVo;
import okhttp3.Request;

import java.util.function.Consumer;

/**
 * 响应的包装,通过该类可以实现链式调用
 *
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

    /**
     * 设置成功的回调
     *
     * @param successConsumer 执行成功的回调不能为null
     * @return 构造器模型, 返回值不为null
     */
    public final ResponseWrapper<T> success(Consumer<T> successConsumer) {
        this.successConsumer = successConsumer;
        return this;
    }

    Consumer<BaseResponse<ErrorVo>> getFailConsumer() {
        return failConsumer;
    }

    /**
     * 设置失败的回调
     *
     * @param failConsumer 执行失败的回调不为null
     * @return 构造器模式, 返回值不为null
     */
    public final ResponseWrapper<T> fail(Consumer<BaseResponse<ErrorVo>> failConsumer) {
        this.failConsumer = failConsumer;
        return this;
    }

    public final void execute() {
        requestHandler.execute(request, this);
    }

}
