package com.ixinrun.lifestyle.widget.step;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.ixinrun.lifestyle.R;
import com.ixinrun.lifestyle.ui.MainActivity;
import com.ixinrun.lifestyle.widget.keep_alive.KeepAliveService;

/**
 * 功能描述: 计步器服务类
 * </p>
 *
 * @author ixinrun
 * @data 2021/3/31
 */
public class StepCounterService extends KeepAliveService {
    private StepDetector mDetector;
    private Notification mNotification;
    private static int mLastStep;

    @Override
    public void onCreate() {
        super.onCreate();
        updateNotification(mLastStep);
        mDetector = new StepDetector(this);
        mDetector.setStepListener(new StepDetector.StepListener() {
            @Override
            public void onStep() {
                mLastStep++;
                updateNotification(mLastStep);
            }
        });
        mDetector.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDetector != null) {
            mDetector.onStop();
        }
        stopForeground(true);
    }

    private void updateNotification(int step) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.SELECT_TAB, 1);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

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
                .setContentTitle("今日：" + step + "步")
                .setContentText("目标：10000步")
                .setContentIntent(pendingIntent)
                .setLargeIcon(bm)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                //不能自动取消
                .setAutoCancel(false)
                //不能滑动删除
                .setOngoing(true);
        mNotification = builder.build();
        //确定service前台运行
        startForeground(1, mNotification);
    }
}
