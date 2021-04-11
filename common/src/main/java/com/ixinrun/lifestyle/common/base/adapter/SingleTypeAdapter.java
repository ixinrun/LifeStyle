package com.ixinrun.lifestyle.common.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 描述: 单一型适配器
 * 适用于仅有一种item布局。
 * </p>
 *
 * @author ixinrun
 * @date 2018/8/21
 */
public abstract class SingleTypeAdapter<T> extends BaseRecycleAdapter<T> {

    private int mLayoutId;

    public SingleTypeAdapter(Context context, int layoutId) {
        super(context);
        this.mLayoutId = layoutId;
    }

    @Override
    public BaseRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(mLayoutId, parent, false);
        return new BaseRecycleHolder(itemView);
    }

    public void setData(List<T> viewModels) {
        mDatas.clear();
        addData(viewModels);
    }

    public void addData(T viewModel) {
        mDatas.add(viewModel);
        notifyDataSetChanged();
    }

    public void addData(int position, T viewModel) {
        mDatas.add(position, viewModel);
        notifyDataSetChanged();
    }

    public void addData(List<T> viewModels) {
        mDatas.addAll(viewModels);
        notifyDataSetChanged();
    }
}
