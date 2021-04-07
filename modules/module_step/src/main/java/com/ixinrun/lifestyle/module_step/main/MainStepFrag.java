package com.ixinrun.lifestyle.module_step.main;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ixinrun.lifestyle.common.base.BaseLsFrag;
import com.ixinrun.lifestyle.common.router.RouterConfig;
import com.ixinrun.lifestyle.module_step.R;
import com.ixinrun.lifestyle.module_step.widget.ArcProgressView;

@Route(path = RouterConfig.ModuleStep.MainStepFrag)
public class MainStepFrag extends BaseLsFrag {
    private ImageView runningIv;
    private ArcProgressView arcView;
    private TextView stepCountTv;

    private AnimationDrawable mAnimation;

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.main_step_frag_layout, null);
        runningIv = view.findViewById(R.id.running_iv);
        stepCountTv = view.findViewById(R.id.step_count);
        arcView = view.findViewById(R.id.arc_view);

        runningIv.setBackgroundResource(R.drawable.anim_running);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) runningIv.getBackground();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        runningIv.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });

        stepCountTv.setText(0 + "");
        arcView.setMaxProgress(100000);
        arcView.setProgress(0, 0);
        arcView.setOnValueChangeListener(new ArcProgressView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                stepCountTv.setText((int) value + "");
            }
        });

        return view;
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    public static MainStepFrag newInstance() {
        Bundle args = new Bundle();

        MainStepFrag fragment = new MainStepFrag();
        fragment.setArguments(args);
        return fragment;
    }
}
