package com.ixinrun.lifestyle.module_more.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ixinrun.lifestyle.common.base.BaseLsFrag;
import com.ixinrun.lifestyle.common.router.RouterConfig;
import com.ixinrun.lifestyle.module_more.R;

@Route(path = RouterConfig.ModuleMore.MainMoreFrag)
public class MainMoreFrag extends BaseLsFrag {
    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.main_more_frag_layout, null);
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    public static MainMoreFrag newInstance() {
        Bundle args = new Bundle();

        MainMoreFrag fragment = new MainMoreFrag();
        fragment.setArguments(args);
        return fragment;
    }
}
