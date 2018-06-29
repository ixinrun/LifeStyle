package com.toperc.keepalive.remote;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.toperc.keepalive.KeepAliveService;

/**
 * 功能描述:
 * </p>
 * 创建人: luoxinrun
 * 创建时间: 2018/6/29
 */
public class DaemonAService extends Service {

    private DaemonBServiceConnection mDaemonBServiceConnection;
    private KeepAliveServiceConnection mKeepAliveServiceConnection;

    @Override
    public void onCreate() {
        super.onCreate();
        mDaemonBServiceConnection = new DaemonBServiceConnection();
        mKeepAliveServiceConnection = new KeepAliveServiceConnection();
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

    class DaemonBServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("TAG", "+++++++++++++++DaemonBService is aliving.");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("TAG", "+++++++++++++++DaemonBService is dead.");
            //开启远程服务
            startService(new Intent(DaemonAService.this, DaemonBService.class));
            //绑定远程服务
            bindService(new Intent(DaemonAService.this, DaemonBService.class), mDaemonBServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    class KeepAliveServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("TAG", "+++++++++++++++KeepAliveService is aliving.");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("TAG", "+++++++++++++++KeepAliveService is aliving.");
            //开启远程服务
            startService(new Intent(DaemonAService.this, KeepAliveService.class));
            //绑定远程服务
            bindService(new Intent(DaemonAService.this, KeepAliveService.class), mKeepAliveServiceConnection, Context.BIND_AUTO_CREATE);
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
