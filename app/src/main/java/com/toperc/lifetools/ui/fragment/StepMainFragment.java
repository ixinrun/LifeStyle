package com.toperc.lifetools.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toperc.lifetools.R;
import com.toperc.lifetools.widget.ArcProgressView;
import com.toperc.lifetools.widget.stepdetector.IStepChangedListener;
import com.toperc.lifetools.widget.stepdetector.StepCounterService;
import com.toperc.lifetools.widget.stepdetector.StepDetector;

/**
 * Created by BigRun on 2016/9/2.
 */
public class StepMainFragment extends Fragment {
    private Activity mActivity;
    private View mContentView;

    private ImageView running_iv;
    private ArcProgressView arc_view;
    private TextView step_count;

    private AnimationDrawable mAnimation;

    private int mLastStep;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_stepmain,null);
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

        //提取数据
        mLastStep = StepCounterService.mLastStep;
        step_count.setText(0 + "");
        arc_view.setMaxProgress(100000);
        arc_view.setProgress(0, mLastStep);
        arc_view.setOnValueChangeListener(new ArcProgressView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                step_count.setText((int) value + "");
                mLastStep = (int) value;
            }
        });

        //动态展示
        StepDetector stepDetector = StepDetector.getInstence(mActivity);
        stepDetector.setStepChangedListener(new IStepChangedListener(){
            @Override
            public void onStepChanged(int currentStep) {
                arc_view.setProgress(mLastStep, currentStep);
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
