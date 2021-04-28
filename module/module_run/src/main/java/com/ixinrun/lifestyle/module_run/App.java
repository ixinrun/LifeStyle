package com.ixinrun.lifestyle.module_run;


import android.app.Application;

import com.ixinrun.base.utils.LoggerUtil;
import com.ixinrun.lifestyle.common.ILsAppCallback;

/**
 * 描述: 该模块下的application
 * </p>
 *
 * @author ixinrun
 * @date 2021/3/31
 */
public class App implements ILsAppCallback {

    @Override
    public void init(Application app) {
        LoggerUtil.i("++++++++++++++++++run");
    }
}
