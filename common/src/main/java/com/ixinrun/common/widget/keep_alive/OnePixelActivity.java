package com.ixinrun.common.widget.keep_alive;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 功能描述: 一像素activity
 * </p>
 *
 * @author ixinrun
 * @data 2021/3/31
 */
public class OnePixelActivity extends Activity {

    private static final String INTENT_ACTION = "activity_finish";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        registerReceiver(mFinishActivityReceiver, new IntentFilter(INTENT_ACTION));
        checkAlive(60 * 1000);
    }

    private void initView() {
        Window window = getWindow();
        //放在左上角
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        //起始坐标
        params.x = 0;
        params.y = 0;
        //宽高设计为1个像素
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
    }

    private void checkAlive(final int delayMillis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    Log.i("TAG", "+++++++++++++++OnePixelScreenActivity is alive.");
                    checkAlive(delayMillis);
                }
            }
        }, delayMillis);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isScreenOn()) {
            finishSelf();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mFinishActivityReceiver);
    }

    /**
     * 动态广播，亮屏时Activity自杀
     */
    BroadcastReceiver mFinishActivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finishSelf();
        }
    };

    /**
     * 自杀
     */
    public void finishSelf() {
        if (!isFinishing()) {
            finish();
        }
    }

    /**
     * 判断主屏幕是否点亮
     *
     * @return
     */
    private boolean isScreenOn() {
        PowerManager powerManager = (PowerManager) getApplicationContext()
                .getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return powerManager.isInteractive();
        } else {
            return powerManager.isScreenOn();
        }
    }

    /**
     * 启动页面
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent screenOffIntent = new Intent(context, OnePixelActivity.class);
        screenOffIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(screenOffIntent);
    }

    /**
     * 关闭页面
     *
     * @param context
     */
    public static void finishActivity(Context context) {
        context.sendBroadcast(new Intent(INTENT_ACTION));
    }
}
