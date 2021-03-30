package com.toperc.keepalive;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.toperc.keepalive.remote.DaemonService;

/**
 * 功能描述:
 * </p>
 * 创建人: Toper-C
 * 创建时间: 2018/6/27
 */
public class KeepAliveHandler {

    private Context mContext;
    private KeepAliveServiceConnection mKeepAliveServiceConnection;
    private DaemonServiceConnection mDaemonServiceConnection;

    private static KeepAliveHandler mInstance = new KeepAliveHandler();

    public static KeepAliveHandler getInstance() {
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mKeepAliveServiceConnection = new KeepAliveServiceConnection();
        this.mDaemonServiceConnection = new DaemonServiceConnection();
        startKeepAliveService();
        startDaemonService();
    }

    private void startKeepAliveService() {
        Intent intent = new Intent(mContext, KeepAliveService.class);
        mContext.startService(intent);
        mContext.bindService(intent, mKeepAliveServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void startDaemonService() {
        Intent intent = new Intent(mContext, DaemonService.class);
        mContext.startService(intent);
        mContext.bindService(intent, mDaemonServiceConnection, Context.BIND_AUTO_CREATE);
    }

    class KeepAliveServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startKeepAliveService();
        }
    }

    class DaemonServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startDaemonService();
        }
    }
}
