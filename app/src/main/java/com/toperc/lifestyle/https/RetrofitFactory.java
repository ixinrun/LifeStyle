package com.toperc.lifestyle.https;

import com.toperc.lifestyle.https.config.HttpConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Toper-C
 * @date 2018/5/23
 * @description 网络请求引擎
 */
public class RetrofitFactory {

    private static RetrofitFactory mRetrofitFactory;
    private Retrofit mRetrofit;

    private RetrofitFactory() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.HTTP_CONNECT_TIME, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_READ_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_WRITE_TIME, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(InterceptorUtil.headerSettingInterceptor())
                .addInterceptor(InterceptorUtil.headeretGettingInterceptor())
                .addInterceptor(InterceptorUtil.logInterceptor())
                .addInterceptor(InterceptorUtil.requestErrorInterceptor())
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl()
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
    }

    public static RetrofitFactory getInstence() {
        synchronized (RetrofitFactory.class) {
            if (mRetrofitFactory == null)
                mRetrofitFactory = new RetrofitFactory();
        }
        return mRetrofitFactory;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
