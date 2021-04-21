package com.ixinrun.lifestyle.module_eat.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ixinrun.lifestyle.common.base.BaseLsFrag;
import com.ixinrun.lifestyle.common.router.RouterConfig;
import com.ixinrun.lifestyle.module_eat.R;

@Route(path = RouterConfig.ModuleEat.MainEatFrag)
public class MainEatFrag extends BaseLsFrag {

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.main_eat_frag_layout, null);
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    public static MainEatFrag newInstance() {
        Bundle args = new Bundle();

        MainEatFrag fragment = new MainEatFrag();
        fragment.setArguments(args);
        return fragment;
    }
}
