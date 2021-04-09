package com.ixinrun.lifestyle.module_main.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

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
        TextView bigWord = findViewById(R.id.big_word);
        TextView smallWord = findViewById(R.id.small_word);

        AlphaAnimation anim = new AlphaAnimation(0.5f, 1f);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(800);
        bigWord.startAnimation(anim);

        AlphaAnimation anim1 = new AlphaAnimation(0.5f, 1f);
        anim1.setInterpolator(new AccelerateInterpolator());
        anim1.setDuration(1600);
        smallWord.startAnimation(anim1);
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
        }, 2000);
    }
}
