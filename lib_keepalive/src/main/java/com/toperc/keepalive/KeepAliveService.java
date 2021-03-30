package com.toperc.keepalive;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.toperc.keepalive.local.OnePixelScreenActivity;

/**
 * 功能描述:
 * </p>
 * 创建人: Toper-C
 * 创建时间: 2018/6/29
 */
public class KeepAliveService extends Service {

    private KeepAliveServiceBinder mKeepAliveServiceBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        registerOnePixelScreenReceiver();
        mKeepAliveServiceBinder = new KeepAliveServiceBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mKeepAliveServiceBinder;
    }

    class KeepAliveServiceBinder extends IDaemonAidlInterface.Stub {

        @Override
        public String getServiceName() throws RemoteException {
            return KeepAliveService.class.getName();
        }
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
                context.sendBroadcast(new Intent(OnePixelScreenActivity.INTENT_ACTION));
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Intent screenOffIntent = new Intent(context, OnePixelScreenActivity.class);
                screenOffIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(screenOffIntent);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mOnePixelScreenReceiver);
    }
}
