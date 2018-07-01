package com.toperc.keepalive.local;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.toperc.keepalive.KeepAliveService;

/**
 * 功能描述:
 * </p>
 * 创建人: Toper-C
 * 创建时间: 2018/6/28
 */
public class SystemReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG", "+++++++++++++++++SystemReceiver onReceive.");
        context.startService(new Intent(context, KeepAliveService.class));
    }
}
