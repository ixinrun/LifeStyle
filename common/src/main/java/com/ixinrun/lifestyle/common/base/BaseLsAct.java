package com.ixinrun.lifestyle.common.base;


import android.content.Context;

import com.ixinrun.base.activity.BaseActivity;
import com.ixinrun.base.activity.view.IBaseView;

/**
 * 描述: 项目Activity基类，所有activity需继承此类。
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/1
 */
public abstract class BaseLsAct extends BaseActivity {

    @Override
    protected IBaseView initBaseViewImpl(Context context, String tag) {
        return new BaseViewImpl(context, tag);
    }
}
