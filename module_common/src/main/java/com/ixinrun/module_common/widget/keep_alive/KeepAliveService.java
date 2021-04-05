package com.ixinrun.module_common.widget.keep_alive;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * 功能描述: 保活服务
 * </p>
 *
 * @author ixinrun
 * @data 2021/3/31
 */
public class KeepAliveService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        registerOnePixelScreenReceiver();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    /**
     * 动态注册OnePixelScreenReceiver
     * 亮屏灭屏不能静态注册
     */
    private void registerOnePixelScreenReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(mOnePixelScreenReceiver, intentFilter);
    }

    /**
     * OnePixelScreenReceiver接收监听
     */
    BroadcastReceiver mOnePixelScreenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                //亮屏时关闭一像素页面
                OnePixelActivity.finishActivity(context);
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                //灭屏时启动一像素页面
                OnePixelActivity.startActivity(context);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mOnePixelScreenReceiver);
    }
}
