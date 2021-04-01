package com.ixinrun.lifestyle;

import androidx.multidex.MultiDexApplication;

/**
 * 功能描述: application
 * </p>
 *
 * @author ixinrun
 * @data 2021/3/31
 */
public class MyApp extends MultiDexApplication {

    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
