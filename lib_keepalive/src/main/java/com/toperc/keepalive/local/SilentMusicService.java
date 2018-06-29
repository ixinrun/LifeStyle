package com.toperc.keepalive.local;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.toperc.keepalive.R;


/**
 * 功能描述:
 * </p>
 * 创建人: luoxinrun
 * 创建时间: 2018/6/27
 */
public class SilentMusicService extends Service implements MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer;
    private Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.silent);
            mediaPlayer.setVolume(0f, 0f);
            mediaPlayer.setOnCompletionListener(this);
        }
        play();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 播放音频
     * 亮屏：播放保活
     * 锁屏：已连接，播音乐；未连接，不播放
     */
    private void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            Log.i("TAG", "+++++++++++++++Silent music is playing.");
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
        startService(new Intent(this, SilentMusicService.class));
    }
}
