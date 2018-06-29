package com.toperc.lifestyle.widget.keepalive;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.toperc.lifestyle.R;

/**
 * 功能描述:
 * </p>
 * 创建人: luoxinrun
 * 创建时间: 2018/6/27
 */
public class SilentMusicProtectService extends Service implements MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer;
    private Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.keep_alive_silent);
            mediaPlayer.setVolume(0f, 0f);
            mediaPlayer.setOnCompletionListener(this);
        }
        play();
        return START_STICKY;
    }

    /**
     * 播放音频
     * 亮屏：播放保活
     * 锁屏：已连接，播音乐；未连接，不播放
     */
    private void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                play();
            }
        }, 60 * 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
