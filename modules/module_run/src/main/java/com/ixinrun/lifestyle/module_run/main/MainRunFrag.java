package com.ixinrun.lifestyle.module_run.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ixinrun.base.bus.LiveDataBus;
import com.ixinrun.lifestyle.common.base.BaseLsFrag;
import com.ixinrun.lifestyle.common.db.AppDatabase;
import com.ixinrun.lifestyle.common.db.dao.StepDao;
import com.ixinrun.lifestyle.common.db.table.DbStepInfo;
import com.ixinrun.lifestyle.common.router.RouterConfig;
import com.ixinrun.lifestyle.module_run.R;
import com.ixinrun.lifestyle.module_run.main.widget.StepChartView;
import com.ixinrun.lifestyle.module_run.main.widget.StepView;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterConfig.ModuleRun.MainRunFrag)
public class MainRunFrag extends BaseLsFrag {

    private StepView mStepView;
    private TextView mDayStepNumTv;
    private TextView mTotalMailTv;
    private TextView mPassDaysTv;
    private StepChartView mStepChartView;
    private FloatingActionButton mRunBtn;

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.main_run_frag_layout, container, false);
        mStepView = view.findViewById(R.id.step_view);
        mDayStepNumTv = view.findViewById(R.id.day_step_num_tv);
        mTotalMailTv = view.findViewById(R.id.total_mail_tv);
        mPassDaysTv = view.findViewById(R.id.pass_days_tv);
        mStepChartView = view.findViewById(R.id.step_chart_view);
        mRunBtn = view.findViewById(R.id.run_btn);
        return view;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mRunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tip("开始跑步！");
            }
        });
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {
        new QueryTask().execute();

        LiveDataBus.get()
                .with("step", Integer.class)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        mStepView.setStepCount(integer);
                    }
                });

        tip("准备好了吗？");
    }

    private class QueryTask extends AsyncTask<Void, Void, Void> {

        public QueryTask() {
        }

        private List<DbStepInfo> tables;
        private List<StepView.StepBean> steps;
        private int dayStepNum;
        private int totalMail;
        private int passDays;

        @Override
        protected Void doInBackground(Void... arg0) {
            StepDao dao = AppDatabase.getInstance(mContext).stepDao();
            tables = dao.getAllData();
            if (tables != null) {
                steps = new ArrayList<>();

                int totalStepNum = 0;
                for (DbStepInfo t : tables) {
                    StepView.StepBean b = new StepView.StepBean();
                    b.setStepCount(t.getStepNum());
                    b.setTargetStepCount(t.getStepNumTarget());
                    steps.add(b);

                    totalStepNum += t.getStepNum();
                    totalMail += t.getKm();
                    passDays += t.isPass() ? 1 : 0;
                }
                dayStepNum = totalStepNum / tables.size();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mStepView.setDatas(steps);
            mDayStepNumTv.setText(String.valueOf(dayStepNum));
            mTotalMailTv.setText(String.valueOf(totalMail));
            mPassDaysTv.setText(String.valueOf(passDays));
            mStepChartView.setData(tables);
        }
    }

    public static MainRunFrag newInstance() {
        Bundle args = new Bundle();

        MainRunFrag fragment = new MainRunFrag();
        fragment.setArguments(args);
        return fragment;
    }
}
