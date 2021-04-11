package com.ixinrun.lifestyle.common.base.adapter;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 描述: BaseRecycleHolder
 * </p>
 *
 * @author ixinrun
 * @date 2018/8/21
 */
public class BaseRecycleHolder extends RecyclerView.ViewHolder {

    private View mConvertView;
    private SparseArray<View> mViews;

    public BaseRecycleHolder(View itemView) {
        super(itemView);
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }

    public View getConvertView() {
        return mConvertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
