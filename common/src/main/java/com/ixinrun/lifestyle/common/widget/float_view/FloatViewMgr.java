package com.ixinrun.lifestyle.common.widget.float_view;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.core.view.ViewCompat;

import com.ixinrun.base.utils.AppUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 悬浮球管理
 * </p>
 *
 * @author xinrun
 * @date 2021/4/15
 */
public class FloatViewMgr {
    private Context mContext;
    private WindowManager mWm;
    private Handler mHandler;
    private Map<Integer, FloatView> mFloatViews;

    private static class SingletonInstance {
        private static final FloatViewMgr INSTANCE = new FloatViewMgr();
    }

    public static FloatViewMgr getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public FloatViewMgr() {
        this.mContext = AppUtil.getApp();
        this.mWm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        this.mFloatViews = new HashMap<>();
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 请求悬浮框权限
     *
     * @return
     */
    public boolean reqPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (Settings.canDrawOverlays(mContext)) {
            return true;
        }

        //延迟主要处理华为等部分手机Settings.canDrawOverlays赋值慢的问题
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + mContext.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        }, 500);
        return false;
    }

    /**
     * 添加悬浮球
     *
     * @param k
     * @param v
     */
    public void addFloatView(int k, FloatView v) {
        if (mFloatViews.get(k) != null) {
            return;
        }
        mFloatViews.put(k, v);
    }

    /**
     * 展示
     */
    public void open(int... keys) {
        if (!reqPermission()) {
            return;
        }

        try {
            for (int k : keys) {
                FloatView view = mFloatViews.get(k);
                if (view != null && !ViewCompat.isAttachedToWindow(view)) {
                    mWm.addView(view, getWindowParams(view));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭
     */
    public void close(int... keys) {
        try {
            for (int k : keys) {
                FloatView view = mFloatViews.get(k);
                if (view != null && ViewCompat.isAttachedToWindow(view)) {
                    mWm.removeView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭所有
     */
    public void closeAll() {
        try {
            for (FloatView view : mFloatViews.values()) {
                if (view != null && ViewCompat.isAttachedToWindow(view)) {
                    mWm.removeView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WindowManager.LayoutParams getWindowParams(View v) {
        WindowManager.LayoutParams mWindowParams = (WindowManager.LayoutParams) v.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mWindowParams.format = PixelFormat.RGBA_8888;
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        return mWindowParams;
    }
}
