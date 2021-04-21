package com.ixinrun.lifestyle.module_main.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.ixinrun.lifestyle.common.base.BaseLsAct;
import com.ixinrun.lifestyle.common.data.UserInfoBean;
import com.ixinrun.lifestyle.common.db.AppDatabase;
import com.ixinrun.lifestyle.common.db.dao.StepDao;
import com.ixinrun.lifestyle.common.db.table.DbStepInfo;
import com.ixinrun.lifestyle.common.mgr.StorageMgr;
import com.ixinrun.lifestyle.module_main.R;

import java.util.Random;

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
        anim1.setDuration(1400);
        smallWord.startAnimation(anim1);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                initUserInfo();
                initDb();
            }
        }).start();

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

    private void initUserInfo() {
        UserInfoBean b = new UserInfoBean();
        b.setUserId("0000");
        b.setUserName("i猩人");
        b.setGender("男");
        b.setBirthday("1991-01-01");
        b.setPhoto("https://api.kdcc.cn/img/");
        b.setHead("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3649178992,1821853682&fm=26&gp=0.jpg");
        b.setMotto("为梦想而奋斗!");
        b.setHeight(178);
        b.setWeight(64);

        StorageMgr.User.put(StorageMgr.User.USER_INFO, b);
    }

    private void initDb() {
        StepDao dao = AppDatabase.getInstance(mContext).stepDao();
        dao.cleanTable();
        for (int i = 0; i < 7; i++) {
            DbStepInfo table = new DbStepInfo();
            table.setStepNum(new Random().nextInt(10000));
            table.setStepNumTarget(new Random().nextInt(1000) + 9000);
            table.setDate("2021-04-1" + i);
            table.setPass(i / 2 == 0);
            table.setKm(new Random().nextInt(10));
            dao.insertItem(table);
        }
    }
}
