package com.toperc.lifestyle.https;

import com.toperc.lifestyle.BuildConfig;
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
    private static volatile RetrofitFactory mRetrofitFactory;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;

    private RetrofitFactory() {
        this.mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.HTTP_CONNECT_TIME, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_READ_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_WRITE_TIME, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(InterceptorUtil.headerSettingInterceptor())
                .addInterceptor(InterceptorUtil.headeretGettingInterceptor())
                .addInterceptor(InterceptorUtil.logInterceptor())
                .addInterceptor(InterceptorUtil.requestErrorInterceptor())
                .build();

        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
    }

    public static RetrofitFactory getInstence() {
        if (mRetrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null)
                    mRetrofitFactory = new RetrofitFactory();
            }
        }
        return mRetrofitFactory;
    }

    public OkHttpClient getOkhttpClient() {
        return mOkHttpClient;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
