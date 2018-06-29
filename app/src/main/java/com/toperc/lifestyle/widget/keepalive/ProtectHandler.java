package com.toperc.lifestyle.widget.keepalive;

import android.content.Context;
import android.content.Intent;

/**
 * 功能描述:
 * </p>
 * 创建人: luoxinrun
 * 创建时间: 2018/6/27
 */
public class ProtectHandler {

    private static ProtectHandler mInstance = new ProtectHandler();

    public static ProtectHandler getInstance() {
        return mInstance;
    }

    public void init(Context context) {
        context.startService(new Intent(context, SilentMusicProtectService.class));
    }
}
