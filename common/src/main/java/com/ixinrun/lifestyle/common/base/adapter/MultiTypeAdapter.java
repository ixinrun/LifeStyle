package com.ixinrun.lifestyle.common.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.collection.ArrayMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述: 复合型适配器
 * 适用于多种item复合布局。
 * </p>
 *
 * @author ixinrun
 * @date 2018/8/21
 */
public abstract class MultiTypeAdapter extends BaseRecycleAdapter<Object> {

    private ArrayList<Integer> mViewTypes;
    private ArrayMap<Integer, Integer> mType2Layout;

    public MultiTypeAdapter(Context context) {
        this(context, null);
    }

    public MultiTypeAdapter(Context context, Map<Integer, Integer> type2Layout) {
        super(context);
        mViewTypes = new ArrayList<>();
        mType2Layout = new ArrayMap<>();
        if (type2Layout != null && !type2Layout.isEmpty()) {
            mType2Layout.putAll(type2Layout);
        }
    }

    @Override
    public BaseRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(mType2Layout.get(viewType), parent, false);
        return new BaseRecycleHolder(itemView);
    }

    public void addView(Integer viewType, Integer layoutResId) {
        mType2Layout.put(viewType, layoutResId);
    }

    @Override
    public int getItemViewType(int position) {
        return mViewTypes.get(position);
    }

    public void setData(int viewType, Object viewModel) {
        mDatas.clear();
        mViewTypes.clear();
        addData(viewType, viewModel);
    }

    public void setData(int viewType, List viewModels) {
        mDatas.clear();
        mViewTypes.clear();
        addData(viewType, viewModels);
    }

    public void addData(int viewType, Object viewModel) {
        mDatas.add(viewModel);
        mViewTypes.add(viewType);
        notifyDataSetChanged();
    }

    public void addData(int position, int viewType, Object viewModel) {
        mDatas.add(position, viewModel);
        mViewTypes.add(position, viewType);
        notifyDataSetChanged();
    }

    public void addData(int viewType, List viewModels) {
        mDatas.addAll(viewModels);
        for (int i = 0; i < viewModels.size(); i++) {
            mViewTypes.add(viewType);
        }
        notifyDataSetChanged();
    }

    @Override
    public void remove(int position) {
        mViewTypes.remove(position);
        super.remove(position);
    }

    @Override
    public void clear() {
        mViewTypes.clear();
        super.clear();
    }

}
