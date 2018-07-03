package com.toperc.keepalive.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.toperc.keepalive.IDaemonAidlInterface;

/**
 * 功能描述:
 * </p>
 * 创建人: Toper-C
 * 创建时间: 2018/6/29
 */
public class DaemonService extends Service {

    private DaemonAServiceBinder mDaemonAServiceBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        mDaemonAServiceBinder = new DaemonAServiceBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mDaemonAServiceBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    class DaemonAServiceBinder extends IDaemonAidlInterface.Stub {

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
