package com.zl.way.util;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttp3Util {

    private static final Logger logger = LoggerFactory.getLogger(OkHttp3Util.class);
    /**
     * 懒汉 安全 加同步 私有的静态成员变量 只声明不创建 私有的构造方法 提供返回实例的静态方法
     */
    private static OkHttpClient okHttpClient = null;

    private OkHttp3Util() {}

    private static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            // 加同步安全
            synchronized (OkHttp3Util.class) {
                if (okHttpClient == null) {

                    okHttpClient = new OkHttpClient.Builder()// 构建器
                        .connectTimeout(15, TimeUnit.SECONDS)// 连接超时
                        .writeTimeout(30, TimeUnit.SECONDS)// 写入超时
                        .readTimeout(30, TimeUnit.SECONDS)// 读取超时
                        .build();
                }
            }

        }

        return okHttpClient;
    }

    public static String getUrlComplete(String url, Map<String, String> parametersMap) {
        Iterator<String> it = parametersMap.keySet().iterator();
        StringBuilder urlSb = new StringBuilder(url);
        urlSb.append("?");
        while (it.hasNext()) {
            String key = it.next();
            String val = parametersMap.get(key);
            urlSb.append(key).append("=").append(val);
            urlSb.append("&");
        }
        urlSb.deleteCharAt(urlSb.lastIndexOf("&"));
        return urlSb.toString();
    }

    public static String doGet(String url, Map<String, String> parametersMap) {

        return doGet(getUrlComplete(url, parametersMap));
    }

    /**
     * get请求 参数1 url 参数2 回调Callback
     */

    public static String doGet(String url) {

        if (logger.isDebugEnabled()) {
            logger.debug("请求地址={}", url);
        }

        // 创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getInstance();
        // 创建Request
        Request request = new Request.Builder().url(url).build();
        // 得到Call对象
        Call call = okHttpClient.newCall(request);
        // 执行同步请求
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                // 得到返回结果
                String responseString = response.body().string();
                if (logger.isDebugEnabled()) {
                    logger.debug("返回结果={}", responseString);
                }
                return responseString;
            }
        } catch (Exception e) {
            logger.error("doGet happens exception: {}", e);
        } finally {
            if (null != response) {
                response.close();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * post请求 参数1 url 参数2 Map<String, String> params post请求的时候给服务器传的数据 add..("","") add()
     */

    public static String doPost(String url, Map<String, String> params) {

        // 创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getInstance();
        // 3.x版本post请求换成FormBody 封装键值对参数

        FormBody.Builder builder = new FormBody.Builder();
        // 遍历集合
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }

        // 创建Request
        Request request = new Request.Builder().url(url).post(builder.build()).build();

        Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {

        } finally {
            if (null != response) {
                response.close();
            }
        }
        return StringUtils.EMPTY;

    }

    public static void main(String[] args) {
        String resp = doGet(
            "http://restapi.amap.com/v3/assistant/inputtips?output=json&city=010&keywords=高楼金第&key=c24b6a839f4603525cc03b9862cf0b42");
        System.out.println(resp);
    }
}
