package com.ixinrun.lifestyle.module_run.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ixinrun.lifestyle.common.base.BaseLsFrag;
import com.ixinrun.lifestyle.common.db.AppDatabase;
import com.ixinrun.lifestyle.common.db.dao.StepDao;
import com.ixinrun.lifestyle.common.db.table.StepTable;
import com.ixinrun.lifestyle.common.router.RouterConfig;
import com.ixinrun.lifestyle.common.widget.LiveDataBus;
import com.ixinrun.lifestyle.module_run.R;
import com.ixinrun.lifestyle.module_run.main.widget.StepView;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterConfig.ModuleRun.MainRunFrag)
public class MainRunFrag extends BaseLsFrag {

    private StepView mStepView;

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.main_run_frag_layout, null);
        mStepView = view.findViewById(R.id.step_view);
        return view;
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
    }

    private class QueryTask extends AsyncTask<Void, Void, List<StepView.StepBean>> {

        public QueryTask() {
        }

        @Override
        protected List<StepView.StepBean> doInBackground(Void... arg0) {
            List<StepView.StepBean> list = new ArrayList<>();
            StepDao dao = AppDatabase.getInstance(mContext).stepDao();
            List<StepTable> tables = dao.getAllData();
            if (tables != null) {
                for (StepTable t : tables) {
                    StepView.StepBean b = new StepView.StepBean();
                    b.setStepCount(t.getStepNum());
                    b.setTargetStepCount(t.getStepNumTarget());
                    list.add(b);
                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<StepView.StepBean> result) {
            super.onPostExecute(result);
            mStepView.setDatas(result);
        }
    }

    public static MainRunFrag newInstance() {
        Bundle args = new Bundle();

        MainRunFrag fragment = new MainRunFrag();
        fragment.setArguments(args);
        return fragment;
    }
}
