package com.ixinrun.lifestyle.common.test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.ixinrun.lifestyle.common.R;
import com.ixinrun.lifestyle.common.base.BaseLsAct;

public abstract class BaseModuleTestActivity extends BaseLsAct {

    @Override
    protected void initView() {
        setContentView(R.layout.module_test_layout);
        //加载Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fl, getFragment())
                .commitAllowingStateLoss();
    }

    protected abstract Fragment getFragment();

    @Override
    protected void loadData(Bundle savedInstanceState) {
    }
}
