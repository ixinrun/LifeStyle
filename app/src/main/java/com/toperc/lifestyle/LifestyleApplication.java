package com.toperc.lifestyle;

import android.app.Application;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by HelloXinrun on 2018/5/7.
 */

public class LifestyleApplication extends Application {

    //绘制页面时参照的设计图尺寸
    public final static float DESIGN_WIDTH = 1080;
    public final static float DESIGN_HEIGHT = 1920;

    @Override
    public void onCreate() {
        super.onCreate();
        resetDensity(DESIGN_WIDTH);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int ori = newConfig.orientation;
        if (ori == newConfig.ORIENTATION_PORTRAIT) {
            //竖屏时重置
            resetDensity(DESIGN_WIDTH);
        } else if (ori == newConfig.ORIENTATION_LANDSCAPE) {
            //横屏时重置
            resetDensity(DESIGN_HEIGHT);
        }
    }

    /**
     * 重置屏幕密度
     *
     * @param width 设计图宽度
     */
    public void resetDensity(float width) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        dm.density = dm.widthPixels / width * dm.density;
    }
}
