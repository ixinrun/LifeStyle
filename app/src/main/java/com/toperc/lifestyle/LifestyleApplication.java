package com.toperc.lifestyle;

import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.toperc.keepalive.KeepAliveHandler;

/**
 * Created by HelloXinrun on 2018/5/7.
 */

public class LifestyleApplication extends MultiDexApplication {

    private static LifestyleApplication instance;

    public static LifestyleApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        resetDensity();
        KeepAliveHandler.getInstance().init(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetDensity();
    }

    /**
     * 重置屏幕密度
     */
    private void resetDensity() {
        //绘制页面时参照的设计图尺寸
        final float DESIGN_WIDTH = 800f;
        final float DESIGN_HEIGHT = 1280f;
        final float DESTGN_INCH = 5.0f;
        //大屏调节因子，范围0~1，因屏幕同比例放大视图显示非常傻大憨，用于调节感官度
        final float BIG_SCREEN_FACTOR = 0.2f;

        DisplayMetrics dm = getResources().getDisplayMetrics();
        //确定放大缩小比率
        float rate = Math.min(dm.widthPixels, dm.heightPixels) / Math.min(DESIGN_WIDTH, DESIGN_HEIGHT);
        //确定参照屏幕密度
        float referenceDensity = (float) Math.sqrt(DESIGN_WIDTH * DESIGN_WIDTH + DESIGN_HEIGHT * DESIGN_HEIGHT) / DESTGN_INCH / 160;
        //确定最终屏幕密度
        float relativeDensity = referenceDensity * rate;
        if (relativeDensity > dm.density) {
            relativeDensity = relativeDensity - (relativeDensity - dm.density) * BIG_SCREEN_FACTOR;
        }
        dm.density = relativeDensity;
    }
}
