package com.ixinrun.module_more.main;

import androidx.fragment.app.Fragment;

import com.ixinrun.common.test.BaseModuleTestActivity;

public class MainActivity extends BaseModuleTestActivity {

    @Override
    protected Fragment getFragment() {
        return MainMoreFrag.newInstance();
    }
}
