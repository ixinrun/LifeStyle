package com.toperc.lifestyle;

import android.support.multidex.MultiDexApplication;

import com.toperc.keepalive.KeepAliveHandler;
import com.toperc.lifestyle.util.ScreenUtil;

/**
 * Created by HelloXinrun on 2018/5/7.
 */

public class LifestyleApplication extends MultiDexApplication {

    private static LifestyleApplication instance;

    public static LifestyleApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ScreenUtil.resetDensity(this);
//        KeepAliveHandler.getInstance().init(this);
    }
}
