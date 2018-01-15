package com.yidan.xiaoaimei.http.utils;


import com.yidan.xiaoaimei.Const;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retofit网络请求工具类
 * Created by HDL on 2016/7/29.
 */
public class RetrofitUtils {
    private static final int READ_TIMEOUT = 60;//读取超时时间,单位  秒
    private static final int CONN_TIMEOUT = 12;//连接超时时间,单位  秒

    private static Retrofit mRetrofit;

    private RetrofitUtils() {

    }

    public static Retrofit newInstence(String url) {
        mRetrofit = null;
//        OkHttpClient client = new OkHttpClient();//初始化一个client,不然retrofit会自己默认添加一个
//        client.setReadTimeout(READ_TIMEOUT, TimeUnit.MINUTES);//设置读取时间为一分钟
//        client.setConnectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS);//设置连接时间为12s\
        mRetrofit = new Retrofit.Builder()
                .client(genericClient())//添加一个client,不然retrofit会自己默认添加一个
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return mRetrofit;
    }


    /**
     * 生成okhttpclient  添加公共头部
     *
     * @return httpClient
     */
    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        okhttp3.Request request = chain.request()
                                .newBuilder()
                                .addHeader("source", "miaoying")
                                .addHeader("appversion", Const.APP_VERSION)
                                .addHeader("system", "android")
                                .build();
                        return chain.proceed(request);
                    }
                }).build();
        return httpClient;
    }


}
