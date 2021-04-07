package com.ixinrun.lifestyle.module_main.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.ixinrun.lifestyle.common.base.BaseLsAct;
import com.ixinrun.lifestyle.module_main.R;

public class SplashActivity extends BaseLsAct {

    @Override
    protected void initBase() {
        super.initBase();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
                finish();
            }
        }, 3000);
    }
}
