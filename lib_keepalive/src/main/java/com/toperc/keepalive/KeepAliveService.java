package com.toperc.keepalive;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.toperc.keepalive.local.OnePixelScreenActivity;
import com.toperc.keepalive.remote.DaemonService;

/**
 * 功能描述:
 * </p>
 * 创建人: Toper-C
 * 创建时间: 2018/6/29
 */
public class KeepAliveService extends Service {
    private DaemonServiceConnection mDaemonServiceConnection;

    @Override
    public void onCreate() {
        super.onCreate();
        startForegroundNotification();
        registerOnePixelScreenReceiver();
        mDaemonServiceConnection = new DaemonServiceConnection();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.toperc.lifestyle","com.toperc.lifestyle.remote.DaemonService"));
        bindService(intent, mDaemonServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Nullable
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
                context.sendBroadcast(new Intent(OnePixelScreenActivity.INTENT_ACTION));
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Intent screenOffIntent = new Intent(context, OnePixelScreenActivity.class);
                screenOffIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(screenOffIntent);
            }
        }
    };

    /**
     * 启动ForegroundNotification
     */
    private void startForegroundNotification() {
        // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//        // 设置点击通知跳转的Intent
//        Intent nfIntent = new Intent(this, MainActivity.class);
//        // 延迟跳转，最后一个参数可以为PendingIntent.FLAG_CANCEL_CURRENT 或者 PendingIntent.FLAG_UPDATE_CURRENT
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfIntent, 0);

        //构建一个Notification构造器
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
        builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                R.drawable.notification_large_icon)) // 设置下拉列表中的图标(大图标)
                .setTicker("您有一条门店PAD通知")  // statusBar上的提示
                .setSmallIcon(R.drawable.notification_small_icon)  // 设置状态栏内的小图标24X24
                .setContentTitle("门店PAD")  // 设置下拉列表里的标题
                .setContentText("KEEP ALIVING...")  // 设置详细内容
                .setShowWhen(false)  //不显示时间
//                .setContentIntent(pendingIntent)  // 设置点击跳转的界面
//                .setWhen(System.currentTimeMillis())  // 设置该通知发生的时间
                .setOngoing(true)  //设置他为一个正在进行的通知
                .setAutoCancel(false)  //单击通知是否自动取消
                .setPriority(Notification.PRIORITY_HIGH);  //优先级高

        Notification notification = builder.build();
        manager.notify(100, notification); //刷新通知

        // 启动前台服务, 参数一：唯一的通知标识；参数二：通知消息。
        startForeground(100, notification);
    }

    class DaemonServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("TAG", "+++++++++++++++DaemonService is aliving.");
            IDaemonAidlInterface iDaemonAidlInterface = IDaemonAidlInterface.Stub.asInterface(service);
            try {
                Log.e("TAG", "+++++++++++++++DaemonService is aliving."+iDaemonAidlInterface.getServiceName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("TAG", "+++++++++++++++DaemonService is onServiceDisconnected.");
            //开启远程服务
            startService(new Intent(KeepAliveService.this, DaemonService.class));
            //绑定远程服务
            bindService(new Intent(KeepAliveService.this, DaemonService.class), mDaemonServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mOnePixelScreenReceiver);
        startService(new Intent(this, KeepAliveService.class));
    }
}
