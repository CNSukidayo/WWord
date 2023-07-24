package io.github.cnsukidayo.wword.common.request;

import io.github.cnsukidayo.wword.token.AuthToken;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 请求处理器<br>
 * 在进行请求之前必须先进行注册,否则产生异常
 *
 * @author sukidayo
 * @date 2023/7/23 15:55
 */
public class RequestRegister {

    private static RequestHandler registerRequestHandler;
    private static AuthToken authToken;

    private RequestRegister() {

    }

    public static void register(RequestHandler requestHandler) {
        registerRequestHandler = Optional.of(requestHandler).orElseThrow(() -> new IllegalArgumentException("RequestHandler不能为空!"));
    }

    public static RequestHandler getRequestHandler() {
        return Optional.of(registerRequestHandler).orElseThrow(() -> new NoSuchElementException(
                "没有注册RequestHandler对象," +
                        "在使用RequestHandler方法进行请求之前;" +
                        "你必须先调用RequestRegister的register方法进行注册."));
    }

    public static boolean hasRequestHandler() {
        return registerRequestHandler == null;
    }


    public static AuthToken getAuthToken() {
        return Optional.of(authToken).orElseGet(AuthToken::new);
    }

    public static void setAuthToken(AuthToken authToken) {
        RequestRegister.authToken = authToken;
    }


}
