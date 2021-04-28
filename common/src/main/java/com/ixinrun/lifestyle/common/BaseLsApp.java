package com.ixinrun.lifestyle.common;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ixinrun.base.BaseApplication;
import com.ixinrun.base.utils.LoggerUtil;

/**
 * 描述: 公共Application
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/6
 */
public class BaseLsApp extends BaseApplication {

    /**
     * 各个模块Application路径注册
     */
    private static final String[] MODULESLIST = {
            "com.ixinrun.lifestyle.App",
            "com.ixinrun.lifestyle.module_run.App",
            "com.ixinrun.lifestyle.module_eat.App",
            "com.ixinrun.lifestyle.module_more.App",
            "com.ixinrun.lifestyle.module_user.App",
    };

    @Override
    public void onCreate() {
        super.onCreate();
        LoggerUtil.init(true, "Lifestyle", null);
        initARouter();
        modulesApplicationInit();
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

    /**
     * 回调各个application
     */
    private void modulesApplicationInit() {
        for (String moduleImpl : MODULESLIST) {
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof ILsAppCallback) {
                    ((ILsAppCallback) obj).init(this);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
