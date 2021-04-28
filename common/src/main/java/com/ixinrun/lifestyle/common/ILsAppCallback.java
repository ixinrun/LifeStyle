package com.ixinrun.lifestyle.common;

import android.app.Application;

/**
 * 描述: 各个模块需要实现的application的接口
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/6
 */
public interface ILsAppCallback {
    /**
     * 初始化
     *
     * @param app
     */
    void init(Application app);

}
