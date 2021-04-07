package com.ixinrun.lifestyle.common;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ixinrun.base.BaseApplication;

/**
 * 描述:
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/6
 */
public class BaseLsApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
    }

    /**
     * 配置ARouter路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
