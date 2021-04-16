package com.ixinrun.lifestyle.common.base.mvp;

import com.ixinrun.base.activity.BaseFragment;

/**
 * 描述:
 * </p>
 *
 * @author xinrun
 * @date 2021/4/16
 */
public abstract class BaseMvpFragment<P extends IPresenter> extends BaseFragment {

    protected P mPresenter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
    }
}
