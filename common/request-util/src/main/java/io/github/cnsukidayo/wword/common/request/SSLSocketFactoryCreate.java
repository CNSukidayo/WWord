package io.github.cnsukidayo.wword.common.request;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author sukidayo
 * @date 2023/7/24 15:42
 */
public class SSLSocketFactoryCreate {

    private SSLSocketFactory sslSocketFactory;

    private X509TrustManager x509TrustManager;

    private final InputStream inputStream;

    private static SSLSocketFactoryCreate sslSocketFactoryCreate;

    public static SSLSocketFactoryCreate newInstance(InputStream inputStream) {
        sslSocketFactoryCreate = new SSLSocketFactoryCreate(inputStream);
        return sslSocketFactoryCreate;
    }

    private SSLSocketFactoryCreate(InputStream inputStream) {
        if (inputStream == null) throw new IllegalArgumentException("inputStream 必须不为null");
        this.inputStream = inputStream;
        initX509TrustManagerAndSSLSocketFactory();
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public X509TrustManager getX509TrustManager() {
        return x509TrustManager;
    }

    /**
     * 初始化
     */
    private void initX509TrustManagerAndSSLSocketFactory() {
        try {
            x509TrustManager = trustManagerForCertificates(inputStream);//以流的方式读入证书
            if (null != x509TrustManager) {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
                sslSocketFactory = sslContext.getSocketFactory();
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 以流的方式添加信任证书
     */

    private X509TrustManager trustManagerForCertificates(InputStream in) throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    /**
     * 添加password
     *
     * @param password
     * @return
     * @throws GeneralSecurityException
     */

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // 这里添加自定义的密码，默认
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }


}
