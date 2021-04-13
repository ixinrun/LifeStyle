package com.ixinrun.lifestyle.common.base;

import android.content.Context;

import com.ixinrun.base.activity.BaseFragment;
import com.ixinrun.base.activity.view.IBaseView;

/**
 * 描述: 项目Fragment基类，所有fragment需继承此类。
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/1
 */
public abstract class BaseLsFrag extends BaseFragment {

    @Override
    protected IBaseView initBaseViewImpl(Context context, String tag) {
        return new BaseViewImpl(context, tag);
    }
}
