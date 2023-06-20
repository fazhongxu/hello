package com.xxl.kit;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 证书工具类
 *
 * @author xxl.
 * @date 2023/6/20.
 */
public class CertificationUtils {

    private CertificationUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取P12证书SSLSocketFactory（证书类型为.p12)
     *
     * @param context  上下文
     * @param rawRes   资源文件ID
     * @param password 证书密码
     * @return
     */
    public static SSLSocketFactory getP12SSLSocketFactory(@NonNull final Context context,
                                                          @RawRes final int rawRes,
                                                          @NonNull final String password) {
        return getP12SSLSocketFactory(context.getResources().openRawResource(rawRes), password);
    }

    /**
     * 获取P12证书SSLSocketFactory（证书类型为.p12)
     *
     * @param inputStream 证书流信息
     * @param password    证书密码
     * @return
     */
    public static SSLSocketFactory getP12SSLSocketFactory(@NonNull final InputStream inputStream,
                                                          @NonNull final String password) {
        SSLSocketFactory sslSocketFactory = null;
        try {
            //keystore添加证书内容和密码
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, password.toCharArray());
            //key管理器工厂
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password.toCharArray());
            //构建一个ssl上下文，加入ca证书格式，与后台保持一致
            SSLContext sslContext = SSLContext.getInstance("TLS");
            //参数，添加受信任证书和生成随机数
            sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

    /**
     * 获取信任的SSLSocketFactory
     *
     * @return
     */
    public static SSLSocketFactory getTrustSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取X509TrustManager
     *
     * @return
     */
    public static X509TrustManager getX509TrustManager() {

        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    /**
     * 获取TrustManager
     *
     * @return
     */
    public static TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{getX509TrustManager()};
        return trustAllCerts;
    }


}