package com.toperc.keepalive.remote;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 功能描述:
 * </p>
 * 创建人: luoxinrun
 * 创建时间: 2018/6/29
 */
public class DaemonBService extends Service {

    private DaemonAServiceConnection mDaemonAServiceConnection;

    @Override
    public void onCreate() {
        super.onCreate();
        mDaemonAServiceConnection = new DaemonAServiceConnection();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    class DaemonAServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("TAG", "+++++++++++++++DaemonAService is aliving.");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("TAG", "+++++++++++++++DaemonAService is aliving.");
            //开启远程服务
            startService(new Intent(DaemonBService.this, DaemonAService.class));
            //绑定远程服务
            bindService(new Intent(DaemonBService.this, DaemonAService.class), mDaemonAServiceConnection, Context.BIND_IMPORTANT);
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
