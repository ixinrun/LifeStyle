package com.ixinrun.lifestyle.module_run.main.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.ixinrun.base.widget.adapter.BaseRecycleHolder;
import com.ixinrun.base.widget.adapter.SingleTypeAdapter;
import com.ixinrun.lifestyle.module_run.R;

import java.util.ArrayList;
import java.util.List;

public class StepView extends FrameLayout {

    private ImageView mRunningIv;
    private TextView mDateTv;
    private ImageView mShareIv;

    private ViewPager2 mStepVp;
    private SingleTypeAdapter mStepVpAdapter;
    private List<StepBean> mDaySteps = new ArrayList<>();

    private boolean mIsRunning;

    public StepView(@NonNull Context context) {
        this(context, null);
    }

    public StepView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initView() {
        View view = View.inflate(getContext(), R.layout.main_run_walking_layout, this);
        mRunningIv = view.findViewById(R.id.running_iv);
        mDateTv = view.findViewById(R.id.date_tv);
        mShareIv = view.findViewById(R.id.share_iv);

        mStepVp = view.findViewById(R.id.step_vp);
        mStepVpAdapter = new StepVpAdapter(getContext());
        mStepVp.setAdapter(mStepVpAdapter);
        mStepVp.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mStepVp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            private static final String FORMAT = "MM月dd日";

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == mStepVpAdapter.getItemCount() - 1) {
                    startStepAnimal(mIsRunning);
                    mDateTv.setText("今天");
                } else {
                    mRunningIv.setImageDrawable(getResources().getDrawable(R.drawable.run_status_stop));
                    mDateTv.setText("昨日");
                }
            }

            private void startStepAnimal(boolean isRunning) {
                AnimationDrawable anim = new AnimationDrawable();
                if (isRunning) {
                    anim.addFrame(getResources().getDrawable(R.drawable.run_status_running0), 150);
                    anim.addFrame(getResources().getDrawable(R.drawable.run_status_running1), 150);
                } else {
                    anim.addFrame(getResources().getDrawable(R.drawable.run_status_running0), 600);
                    anim.addFrame(getResources().getDrawable(R.drawable.run_status_running1), 600);
                }
                anim.setOneShot(false);
                mRunningIv.setImageDrawable(anim);
                anim.start();
            }
        });
    }

    static class StepVpAdapter extends SingleTypeAdapter<StepBean> {

        public StepVpAdapter(Context context) {
            super(context, R.layout.main_run_step_item);
        }

        @Override
        public void onBindView(BaseRecycleHolder holder, StepBean b, int position, int viewType) {
            ArcProgressView stepProgressView = holder.getView(R.id.step_progress_view);
            TextView stepCountTv = holder.getView(R.id.step_count_tv);
            TextView calorieTv = holder.getView(R.id.calorie_tv);
            TextView distanceTv = holder.getView(R.id.distance_tv);
            TextView targetStepCountTv = holder.getView(R.id.target_step_count_tv);

            stepProgressView.setMaxProgress(b.getTargetStepCount());
            stepProgressView.setProgress(b.getLastStepCount(), b.getStepCount());
            stepCountTv.setText(String.valueOf(b.getStepCount()));
            float km = getKm(178, b.getStepCount());
            float kc = getKc(68, km);
            calorieTv.setText((float) (Math.round(kc * 100)) / 100 + "千卡");
            distanceTv.setText((float) (Math.round(km * 100)) / 100 + "公里");
            targetStepCountTv.setText("目标：" + b.getTargetStepCount() + "步");
        }

        /**
         * 根据身高和步数获取总公里数
         *
         * @param height    身高
         * @param stepCount 步数
         * @return 总公里数
         */
        private float getKm(int height, int stepCount) {
            float stepM;
            if (height < 160) {
                stepM = 0.5f;
            } else if (height >= 160 && height < 180) {
                stepM = 0.67f;
            } else {
                stepM = 0.75f;
            }
            return stepCount * stepM / 1000;
        }

        /**
         * 根据体重和总距离计算千卡
         * <p>
         * 1MET（脱敏，身体活动强度）相当于每公斤体重每小时消耗1千卡能量。
         * 慢速：每小时3公里，活动强度是2.5
         * 中速：每小时5公里，活动强度是3.5
         * 中速：每小时5.5-6公里，活动强度是4.0
         * 快速：每小时7公里，活动强度是4.5
         * https://zhuanlan.zhihu.com/p/262225205
         * </p>
         *
         * @param weight 其中
         * @param km     距离
         * @return 千卡
         */
        private float getKc(int weight, float km) {
            final float MET = 3f;
            final float HOUR_KM = 3.618f;
            return (km / HOUR_KM) * weight * MET;
        }
    }

    public static class StepBean {
        private int stepCount;
        private int lastStepCount;
        private int targetStepCount;

        public int getStepCount() {
            return stepCount;
        }

        public void setStepCount(int stepCount) {
            this.stepCount = stepCount;
        }

        public int getLastStepCount() {
            return lastStepCount;
        }

        public void setLastStepCount(int lastStepCount) {
            this.lastStepCount = lastStepCount;
        }

        public int getTargetStepCount() {
            return targetStepCount;
        }

        public void setTargetStepCount(int targetStepCount) {
            this.targetStepCount = targetStepCount;
        }
    }

    public void setDatas(List<StepBean> b) {
        mDaySteps.clear();
        mDaySteps.addAll(b);
        mStepVpAdapter.setData(mDaySteps);
        mStepVp.setCurrentItem(mDaySteps.size() - 1);
    }

    /**
     * 设置步数
     *
     * @param count
     */
    public void setStepCount(int count) {
        int last = mStepVpAdapter.getItemCount() - 1;
        StepBean b = (StepBean) mStepVpAdapter.getDatas().get(last);
        b.setLastStepCount(b.getStepCount());
        b.setStepCount(count);

        // 刷新界面
        if (mStepVp.getCurrentItem() == last) {
            mStepVpAdapter.notifyItemChanged(last, 0);
        }
    }

    /**
     * 设置记步状态
     *
     * @param isRunning 奔跑状态
     */
    public void setStepStatus(boolean isRunning) {
        this.mIsRunning = isRunning;
    }
}
