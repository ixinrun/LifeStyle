package com.toperc.lifestyle.https;

import android.content.Context;
import android.util.Log;

import com.toperc.lifestyle.LifestyleApplication;
import com.toperc.lifestyle.R;
import com.toperc.lifestyle.https.exception.ApiException;
import com.toperc.lifestyle.https.exception.TokenExpiredException;
import com.toperc.lifestyle.util.NetworkUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author Toper-C
 * @date 2018/5/25
 * @description
 */
public class InterceptorUtil {
    public static String TAG = "----";
    public static Context mContext = LifestyleApplication.getInstance().getApplicationContext();

    //日志拦截器
    public static HttpLoggingInterceptor logInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(TAG, "log: " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    //头部设置拦截器
    public static Interceptor headerSettingInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request mRequest = chain.request();


                return chain.proceed(mRequest);
            }
        };
    }

    //头部获取拦截器
    public static Interceptor headeretGettingInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return null;
            }
        };
    }

    //错误信息拦截器
    public static Interceptor requestErrorInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                if (!NetworkUtil.isConnected(mContext)) {
                    throw new ApiException(0, mContext.getString(R.string.newwork_err));
                }
                Request request = chain.request();
                Response response = chain.proceed(request);
                ApiException e = null;
                if (401 == response.code()) {
                    throw new TokenExpiredException(401, mContext.getString(R.string.newwork_request_err_401));
                } else if (403 == response.code()) {
                    e = new ApiException(403, mContext.getString(R.string.newwork_request_err_403));
                } else if (503 == response.code()) {
                    e = new ApiException(503, mContext.getString(R.string.newwork_request_err_503));
                } else if (500 == response.code()) {
                    e = new ApiException(500, mContext.getString(R.string.newwork_request_err_500));
                } else if (404 == response.code()) {
                    e = new ApiException(404, mContext.getString(R.string.newwork_request_err_404));
                }
                if (e != null) {
                    throw e;
                }
                return response;
            }
        };
    }
}
