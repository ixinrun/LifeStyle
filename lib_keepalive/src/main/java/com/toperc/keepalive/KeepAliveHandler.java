package com.toperc.keepalive;

import android.content.Context;
import android.content.Intent;

import com.toperc.keepalive.local.SilentMusicService;

/**
 * 功能描述:
 * </p>
 * 创建人: luoxinrun
 * 创建时间: 2018/6/27
 */
public class KeepAliveHandler {

    private static KeepAliveHandler mInstance = new KeepAliveHandler();

    public static KeepAliveHandler getInstance() {
        return mInstance;
    }

    public void init(Context context) {
        context.startService(new Intent(context, SilentMusicService.class));
    }
}
