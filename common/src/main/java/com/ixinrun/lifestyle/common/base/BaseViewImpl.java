package com.ixinrun.lifestyle.common.base;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ixinrun.base.activity.view.IBaseView;

/**
 * 描述: BaseView的实现类
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/6
 */
public class BaseViewImpl implements IBaseView {
    private Context mContext;
    private String mTag;

    BaseViewImpl(Context context, String tag) {
        this.mContext = context;
        this.mTag = tag;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showProgress(boolean cancelable) {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void tip(@NonNull String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tip(int resId) {

    }

    @Override
    public void tip(@NonNull String msg, @NonNull TipEnum tipEnum) {

    }

    @Override
    public void tip(int resId, @NonNull TipEnum tipEnum) {

    }

    @Override
    public void onViewDestroy() {

    }
}
