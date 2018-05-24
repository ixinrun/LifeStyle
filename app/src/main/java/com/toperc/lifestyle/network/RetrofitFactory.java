package com.toperc.lifestyle.network;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.toperc.lifestyle.network.config.HttpConfig;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Toper-C
 * @date 2018/5/23
 * @description 网络请求引擎
 */
public class RetrofitFactory {

    private static Context context;
    private static String baseUrl;
    // private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd
    // HH:mm:ss").create();
    private static Gson gson = GsonUtil.getGsonInstance(false);

    public static void init(Context context, String baseUrl) {
        RetrofitFactory.context = context;
        RetrofitFactory.baseUrl = baseUrl;
    }

    private static Interceptor setUserCookie = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request;
            if (!TextUtils.isEmpty(TokenMgr.getToken())) {
                request = chain.request().newBuilder().addHeader("Cookie", TokenMgr.getToken())
                        .addHeader("trace_id", getTraceId()).build();
            } else {
                request = chain.request().newBuilder().addHeader("trace_id", getTraceId()).build();
            }
            return chain.proceed(request);
        }
    };

    private static String getTraceId() {
        if (SessionMgr.getUser() != null && SessionMgr.getUser().getId() != null) {
            return SessionMgr.getUser().getId() + "-" + System.currentTimeMillis();
        } else {
            return UUID.randomUUID().toString();
        }
    }

    private static Interceptor getUserCookie = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            String cookie = response.headers().get("Set-Cookie");
            if (!IsEmpty.string(cookie) && cookie.contains("Jwt=") && TokenMgr.isExpired()) {
                TokenMgr.setToken(cookie);
            }
            return response;
        }
    };

    private static Interceptor requestErrorInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            if (!NetworkUtil.isConnected(context)) {
                throw new ApiException(0, context.getString(R.string.newwork_err));
            }
            Request request = chain.request();
            Response response = chain.proceed(request);
            ApiException e = null;
            if (401 == response.code()) {
                throw new TokenExpiredException(401, context.getString(R.string.newwork_request_err_401));
            } else if (403 == response.code()) {
                e = new ApiException(403, context.getString(R.string.newwork_request_err_403));
            } else if (503 == response.code()) {
                e = new ApiException(503, context.getString(R.string.newwork_request_err_503));
            } else if (500 == response.code()) {
                e = new ApiException(500, context.getString(R.string.newwork_request_err_500));
            } else if (404 == response.code()) {
                e = new ApiException(404, context.getString(R.string.newwork_request_err_404));
            }
            if (e != null) {
                throw e;
            }

            return response;
        }
    };

    public static Retrofit instance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okClient = new OkHttpClient.Builder().retryOnConnectionFailure(true)
                .addInterceptor(setUserCookie).addInterceptor(new ErrorLogInterceptor())
                .addInterceptor(logging).addInterceptor(new MockInterceptor(context))
                .addInterceptor(requestErrorInterceptor).addInterceptor(getUserCookie)
                .connectTimeout(60, TimeUnit.SECONDS).retryOnConnectionFailure(true)
                .writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder().client(okClient).baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        return retrofit;
    }


    private static RetrofitFactory mRetrofitFactory;

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
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
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
}
