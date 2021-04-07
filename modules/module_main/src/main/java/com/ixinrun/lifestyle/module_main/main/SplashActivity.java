package com.ixinrun.lifestyle.module_main.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;

import com.ixinrun.lifestyle.common.base.BaseLsAct;
import com.ixinrun.lifestyle.module_main.R;

public class SplashActivity extends BaseLsAct {

    @Override
    protected boolean isTranslucentStatusBar() {
        return true;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
        View contentView = findViewById(R.id.content_ll);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setDuration(1500);
        contentView.startAnimation(alphaAnimation);
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
