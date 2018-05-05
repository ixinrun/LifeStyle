package com.toperc.lifestyle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.toperc.lifestyle.R;
import com.toperc.lifestyle.base.BaseActivity;
import com.toperc.lifestyle.widget.stepdetector.StepCounterService;

/**
 * Created by BigRun on 2016/9/1.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //开启计步服务
        Intent intent = new Intent(SplashActivity.this, StepCounterService.class);
        startService(intent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.trans_right_in,R.anim.trans_left_out);
                finish();
            }
        }, 3000);
    }
}
