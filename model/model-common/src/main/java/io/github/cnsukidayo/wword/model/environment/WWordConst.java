package io.github.cnsukidayo.wword.model.environment;

import org.springframework.http.HttpHeaders;

import java.util.Set;

/**
 * @author sukidayo
 * @date 2023/5/17 17:06
 */
public class WWordConst {

    /**
     * 用户携带token的请求头
     */
    public static final String API_ACCESS_KEY_HEADER_NAME = "API-" + HttpHeaders.AUTHORIZATION;

    /**
     * 网关转发token时使用的请求头
     */
    public static final String X_CLIENT_USER = "X-CLIENT-USER";

    /**
     * 用户昵称前缀
     */
    public static final String USER_NICK_PREFIX = "用户_";

    /**
     * 路径分隔符
     */
    public static char separatorChar = '/';

    /**
     * 允许用户上传的markdown文件后缀
     */
    public static final Set<String> allowMarkdownSuffix = Set.of("png", "jpg", "gif", "md", "MD", "PNG", "JPG", "JPEG", "GIF");

    /**
     * 允许用于发布的文件后缀
     */
    public static final Set<String> allowPublicSuffix = Set.of("png", "jpg", "gif", "jpeg", "PNG", "JPG", "JPEG", "GIF");


    public static final String dateFormat = "yyyy/MM/dd";

}
