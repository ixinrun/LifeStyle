package com.ixinrun.lifestyle;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.ixinrun.base.bus.LiveDataBus;
import com.ixinrun.base.service.KeepAliveService;
import com.ixinrun.lifestyle.main.MainActivity;
import com.ixinrun.lifestyle.module_run.main.util.StepDetector;

/**
 * 描述: App常驻Service
 * </p>
 *
 * @author ixinrun
 * @date 2021/3/31
 */
public class AppService extends KeepAliveService {
    private StepDetector mDetector;
    private Notification mNotification;

    @Override
    public void onCreate() {
        super.onCreate();
        updateNotification(0);
        mDetector = new StepDetector(this);
        mDetector.setStepListener(new StepDetector.StepListener() {
            int step;

            @Override
            public void onStep() {
                step++;
                LiveDataBus.get().with("step", Integer.class).setValue(step);
                updateNotification(step);
            }
        });
        mDetector.onStart();
    }

    private void updateNotification(int step) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String ID = "AppService";
        String NAME = "前台服务";
        NotificationCompat.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, ID);
        } else {
            builder = new NotificationCompat.Builder(this, null);
        }

        builder.setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("今日步数：" + step + "步")
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.notify_small_icon)
                .setWhen(System.currentTimeMillis())
                //不能自动取消
                .setAutoCancel(false)
                //不能滑动删除
                .setOngoing(true);
        mNotification = builder.build();
        //确定service前台运行
        startForeground(1, mNotification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDetector != null) {
            mDetector.onStop();
        }
        stopForeground(true);
    }
}
