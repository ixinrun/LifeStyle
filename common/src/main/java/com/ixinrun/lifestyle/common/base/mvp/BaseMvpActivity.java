package com.ixinrun.lifestyle.common.base.mvp;

import com.ixinrun.base.activity.BaseActivity;

/**
 * 描述:
 * </p>
 *
 * @author xinrun
 * @date 2021/4/16
 */
public abstract class BaseMvpActivity<P extends IPresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
    }
}