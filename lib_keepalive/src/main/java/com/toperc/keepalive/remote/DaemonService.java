package com.toperc.keepalive.remote;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.toperc.keepalive.IDaemonAidlInterface;
import com.toperc.keepalive.KeepAliveService;

/**
 * 功能描述:
 * </p>
 * 创建人: Toper-C
 * 创建时间: 2018/6/29
 */
public class DaemonService extends Service {

    private DaemonAServiceBinder mDaemonAServiceBinder;
    private KeepAliveServiceConnection mKeepAliveServiceConnection;

    @Override
    public void onCreate() {
        super.onCreate();
        mDaemonAServiceBinder = new DaemonAServiceBinder();
        mKeepAliveServiceConnection = new KeepAliveServiceConnection();

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.toperc.lifestyle","com.toperc.lifestyle.KeepAliveService"));
        bindService(intent, mKeepAliveServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mDaemonAServiceBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    class KeepAliveServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("TAG", "+++++++++++++++KeepAliveService is aliving.");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("TAG", "+++++++++++++++KeepAliveService is onServiceDisconnected.");
            //开启远程服务
            startService(new Intent(DaemonService.this, KeepAliveService.class));
            //绑定远程服务
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.toperc.keepalive","com.toperc.keepalive.KeepAliveService"));
            bindService(intent, mKeepAliveServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    class DaemonAServiceBinder extends IDaemonAidlInterface.Stub{

        @Override
        public String getServiceName() throws RemoteException {
            return DaemonService.class.getName();
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
