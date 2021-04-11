package com.ixinrun.lifestyle.common.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: BaseRecycleAdapter
 * </p>
 *
 * @author ixinrun
 * @date 2018/8/21
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleHolder> {

    protected List<T> mDatas;
    protected final LayoutInflater mLayoutInflater;

    public BaseRecycleAdapter(Context context) {
        this.mDatas = new ArrayList<>();
        this.mLayoutInflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }

    @Override
    public void onBindViewHolder(BaseRecycleHolder holder, int position) {
        onBindView(holder, mDatas.get(position), position, getItemViewType(position));
    }

    /**
     * 解决notifyDataSetChanged刷新闪烁问题:
     * "setHasStableIds(true) + 重写getItemId()"。
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        if (hasStableIds()) {
            return position;
        }
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDatas.size());
    }

    public void remove(T t) {
        int index = mDatas.indexOf(t);
        if (index != -1) {
            remove(index);
        }
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public abstract void onBindView(BaseRecycleHolder holder, T t, int position, int viewType);

}
