package com.ixinrun.lifestyle.common.base.mvp;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.ixinrun.base.activity.view.IBaseView;
import com.ixinrun.lifestyle.common.base.BaseLifecycle;

/**
 * 描述:
 * </p>
 *
 * @author xinrun
 * @date 2021/4/16
 */
public class BasePresenter<V extends IBaseView> extends BaseLifecycle implements IPresenter {

    protected V mView;

    public BasePresenter(@NonNull V view) {
        this.mView = view;
        onAttach();
    }

    @Override
    public void onAttach() {
        if (mView != null && mView instanceof LifecycleOwner) {
            addObserver((LifecycleOwner) mView);
        }
    }

    @Override
    public void onDetach() {

    }
}
