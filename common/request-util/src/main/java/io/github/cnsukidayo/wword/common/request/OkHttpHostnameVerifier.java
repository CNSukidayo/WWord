package io.github.cnsukidayo.wword.common.request;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class OkHttpHostnameVerifier implements HostnameVerifier {

    /**
     * OkHttp验证主机名
     *
     * @return false 不信任主机
     * 默认 false
     */

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
//        HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
//        return hv.verify(hostname, session);
    }
}