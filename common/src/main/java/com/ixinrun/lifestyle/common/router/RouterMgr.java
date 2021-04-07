package com.ixinrun.lifestyle.common.router;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 描述: 路由跳转管理
 * </p>
 *
 * @author xinrun
 * @date 2021/4/7
 */
public class RouterMgr {

    /**
     * 跳转到module_main主页面
     */
    public static void toModuleMain() {
        ARouter.getInstance().build(RouterConfig.ModuleMain.MainActivity).navigation();
    }


}
