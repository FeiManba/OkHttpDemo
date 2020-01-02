package com.huifeideyema.net;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author : mr.zang
 * description 网络调用封装类
 * 基于OkHttp3.^
 * createDate: 2020-01-02 16:52
 */
public final class NetManager {
    private static NetManager instance;

    /**
     * 是否初始化 OkHttp
     */
    private boolean isInit;
    private OkHttpClient.Builder builder;

    private NetManager() {
        if (!isInit) {
            initOkHttp();
            isInit = true;
        }
    }

    private void initOkHttp() {
        builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
    }

    public static NetManager getInstance() {
        if (instance == null) {
            synchronized (NetManager.class) {
                instance = new NetManager();
            }
        }
        return instance;
    }

    /**
     * 下载
     * 异步（根据断点请求）
     *
     * @param url
     * @param start
     * @param end
     * @param callback
     * @return
     */
    public void initRequest(String url, long start, long end, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .header("Range", "bytes=" + start + "-" + end)
                .build();
        Call call = builder.build().newCall(request);
        call.enqueue(callback);
    }

    /**
     * GET 请求
     *
     * @param url      请求的路径
     * @param callback 请求返回 接口
     * @return
     */
    public void get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "text/plain")
                .header("Content-Type", "UTF-8")
                .build();

        Call call = builder.build().newCall(request);
        call.enqueue(callback);
    }

    /**
     * POST 请求
     *
     * @param url      请求的路径
     * @param json     请求Json Model
     * @param callback 请求返回 接口
     */
    public void post(String url, String json, Callback callback) {
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; " +
                        "charset=utf-8")
                , json);
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "text/plain")
                .header("Content-Type", "UTF-8")
                .post(requestBody)
                .build();
        Call call = builder.build().newCall(request);
        call.enqueue(callback);
    }

    /**
     * 下载
     * 同步请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public Response initRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("Range", "bytes=0-")
                .build();
        return builder.build().newCall(request).execute();
    }

    /**
     * 下载
     * 文件存在的情况下可判断服务端文件是否已经更改
     *
     * @param url
     * @param lastModify
     * @return
     * @throws IOException
     */
    public Response initRequest(String url, String lastModify) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("Range", "bytes=0-")
                .header("If-Range", lastModify)
                .build();

        return builder.build().newCall(request).execute();
    }

    /**
     * 下载
     * https请求时初始化证书
     *
     * @param certificates
     * @return
     */
    public void setCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传
     * 异步
     *
     * @param url
     * @param requestBody
     * @param headers
     * @param callback
     * @return
     */
    public void initRequest(String url, RequestBody requestBody, Map<String, String> headers, Callback callback) {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(requestBody);
        if (headers != null && headers.size() > 0) {
            Headers.Builder headerBuilder = new Headers.Builder();
            for (String key : headers.keySet()) {
                headerBuilder.add(key, headers.get(key));
            }
            requestBuilder.headers(headerBuilder.build());
        }
        Call call = builder.build().newCall(requestBuilder.build());
        call.enqueue(callback);
    }
}
