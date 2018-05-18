package com.toperc.lifestyle;

import android.app.Application;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by HelloXinrun on 2018/5/7.
 */

public class LifestyleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        resetDensity(Configuration.ORIENTATION_PORTRAIT);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetDensity(newConfig.orientation);
    }

    /**
     * 重置屏幕密度
     *
     * @param orientation
     */
    private void resetDensity(int orientation) {
        //绘制页面时参照的设计图尺寸
        final float DESIGN_WIDTH = 1080f;
        final float DESIGN_HEIGHT = 1920f;
        final float DESTGN_INCH = 4.91f;
        //获取参考密度值
        float referenceDensity = (float) Math.sqrt(DESIGN_WIDTH * DESIGN_WIDTH + DESIGN_HEIGHT * DESIGN_HEIGHT) / DESTGN_INCH / 160;
        //获取放大缩小比率
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float rate = 1f;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            rate = dm.widthPixels / DESIGN_WIDTH;
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rate = dm.widthPixels / DESIGN_HEIGHT;
        }
        //重置系统密度值
        dm.density = referenceDensity * rate;
    }
}
