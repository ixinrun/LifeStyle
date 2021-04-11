package com.ixinrun.lifestyle.module_run.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ixinrun.lifestyle.common.base.BaseLsFrag;
import com.ixinrun.lifestyle.common.router.RouterConfig;
import com.ixinrun.lifestyle.module_run.R;

@Route(path = RouterConfig.ModuleRun.MainRunFrag)
public class MainRunFrag extends BaseLsFrag {

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.main_run_frag_layout, null);
        // todo.....
        return view;
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {
    }

    public static MainRunFrag newInstance() {
        Bundle args = new Bundle();

        MainRunFrag fragment = new MainRunFrag();
        fragment.setArguments(args);
        return fragment;
    }
}
