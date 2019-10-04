package com.androidlab.shiji.utils;

import android.app.Application;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class OkGoUtils extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 默认使用OkGo的封装
         * 使用的是OkHttpClient 来进行网络请求
         * */
        OkGo.getInstance().init(this);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        /**
         * 日志配置
         * */
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
         // 确定Log的打印级别
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        // log的颜色级别 决定了 log的在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        /**
         * 超时 配置
         * */

        // 全局读取的超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MICROSECONDS);
        // 全局的写入超时 时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS,TimeUnit.MICROSECONDS);
        // 全局连接的超时的时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MICROSECONDS);


    }

    /**
     * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
     * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
     * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
     * 重要的事情说三遍，以下代码不要直接使用
     */
    private class SafeTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                for (X509Certificate certificate : chain) {
                    certificate.checkValidity(); //检查证书是否过期，签名是否通过等
                }
            } catch (Exception e) {
                throw new CertificateException(e);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /**
     * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
     * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
     * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
     * 重要的事情说三遍，以下代码不要直接使用
     */
    private class SafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            //验证主机名是否匹配
            //return hostname.equals("server.jeasonlzy.com");
            return true;
        }
    }

}
