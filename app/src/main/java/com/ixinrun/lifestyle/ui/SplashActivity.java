package com.ixinrun.lifestyle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.ixinrun.app_base.activity.BaseActivity;
import com.ixinrun.lifestyle.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void initBeforeContent() {
        super.initBeforeContent();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected boolean isTranslucentStatusBar() {
        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initComponent() {
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
