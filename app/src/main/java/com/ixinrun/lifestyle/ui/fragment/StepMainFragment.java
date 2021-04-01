package com.ixinrun.lifestyle.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ixinrun.lifestyle.R;
import com.ixinrun.lifestyle.base.BaseFragment;
import com.ixinrun.lifestyle.widget.ArcProgressView;

public class StepMainFragment extends BaseFragment {

    private Activity mActivity;
    private View mContentView;
    private ImageView running_iv;
    private ArcProgressView arc_view;
    private TextView step_count;

    private AnimationDrawable mAnimation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_stepmain, null);
        initView();

        running_iv.setBackgroundResource(R.drawable.anim_running);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) running_iv.getBackground();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        running_iv.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });

        step_count.setText(0 + "");
        arc_view.setMaxProgress(100000);
        arc_view.setProgress(0, 0);
        arc_view.setOnValueChangeListener(new ArcProgressView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                step_count.setText((int) value + "");
            }
        });

        return mContentView;
    }

    private void initView() {
        running_iv = (ImageView) mContentView.findViewById(R.id.running_iv);
        step_count = (TextView) mContentView.findViewById(R.id.step_count);
        arc_view = (ArcProgressView) mContentView.findViewById(R.id.arc_view);
    }
}
