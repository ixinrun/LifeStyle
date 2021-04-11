package com.ixinrun.lifestyle;

import android.content.Intent;

import com.ixinrun.lifestyle.common.BaseLsApp;

public class App extends BaseLsApp {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, AppService.class));
    }
}
