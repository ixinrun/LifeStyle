package com.ixinrun.lifestyle.module_main.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.ixinrun.base.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 底部导航
 * </p>
 *
 * @author ixinrun
 * @date 2020/6/8
 */
public class MainNavigationBar extends LinearLayout {
    private final List<NavigationItem> mItems = new ArrayList<>();
    private TabSwitchCallback mCallback;

    private int tabResSize = 27;
    private int tagTextSize = 12;
    private int tabTextColor0 = Color.GRAY;
    private int tabTextColor1 = Color.DKGRAY;


    public MainNavigationBar(Context context) {
        super(context);
    }

    public MainNavigationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setPadding(0, dp2px(5), 0, dp2px(5));
    }

    /**
     * 设置tab字体颜色
     *
     * @param color0 默认颜色
     * @param color1 选中的颜色
     */
    public MainNavigationBar setTabTextColor(@ColorInt int color0, @ColorInt int color1) {
        this.tabTextColor0 = color0;
        this.tabTextColor1 = color1;
        return this;
    }

    /**
     * 添加tab
     *
     * @param tabSrc0 默认图片
     * @param tabSrc1 选中的图片
     * @param tabName tab文字
     */
    public MainNavigationBar addTab(@DrawableRes int tabSrc0, @DrawableRes int tabSrc1, String tabName) {
        NavigationItem item = new NavigationItem(getContext(), tabSrc0, tabSrc1, tabName);
        item.setOnClickListener(v -> switchTab(mItems.indexOf(item)));
        addView(item, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));
        mItems.add(item);
        return this;
    }

    private class NavigationItem extends LinearLayout {

        private final ImageView mNavIv;
        private final TextView mNavTv;

        private final int tabSrc0;
        private final int tabSrc1;
        private boolean isChecked;

        public NavigationItem(Context context, @DrawableRes int tabSrc0, @DrawableRes int tabSrc1, String tabName) {
            super(context);
            this.tabSrc0 = tabSrc0;
            this.tabSrc1 = tabSrc1;

            setOrientation(VERTICAL);
            setGravity(Gravity.CENTER);

            mNavIv = new ImageView(context);
            mNavIv.setLayoutParams(new LayoutParams(dp2px(tabResSize), dp2px(tabResSize)));
            addView(mNavIv);

            mNavTv = new TextView(context);
            mNavTv.setText(tabName);
            mNavTv.setTextSize(tagTextSize);
            LayoutParams lp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, dp2px(6), 0, 0);
            mNavTv.setLayoutParams(lp);

            if (!TextUtils.isEmpty(tabName)) {
                addView(mNavTv);
            }

            setChecked(false);
        }

        public void setChecked(boolean isChecked) {
            this.isChecked = isChecked;
            if (!isChecked) {
                mNavIv.setImageResource(tabSrc0);
                mNavTv.setTextColor(tabTextColor0);
            } else {
                mNavIv.setImageResource(tabSrc1);
                mNavTv.setTextColor(tabTextColor1);
            }
        }

        public boolean isChecked() {
            return isChecked;
        }
    }

    private int dp2px(int dp) {
        return DensityUtil.dpi2px(getContext(), dp);
    }

    /**
     * tab切換
     *
     * @param index tab下标
     */
    public void switchTab(int index) {
        if (index > mItems.size() - 1) {
            return;
        }
        NavigationItem tab = mItems.get(index);
        if (tab == null || tab.isChecked()) {
            return;
        }
        for (NavigationItem item : mItems) {
            item.setChecked(false);
        }
        tab.setChecked(true);
        if (mCallback != null) {
            mCallback.onSwitched(tab, index);
        }
    }

    /**
     * tab切換回調
     *
     * @param callback 切换回调监听
     * @return this
     */
    public MainNavigationBar setSwitchCallback(TabSwitchCallback callback) {
        this.mCallback = callback;
        return this;
    }

    public interface TabSwitchCallback {
        /**
         * tab切换回调
         *
         * @param view
         * @param index
         */
        void onSwitched(View view, int index);
    }
}
