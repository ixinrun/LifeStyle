package com.ixinrun.lifestyle.module_more.main;

import androidx.fragment.app.Fragment;

import com.ixinrun.lifestyle.common.test.BaseModuleTestActivity;

public class MainActivity extends BaseModuleTestActivity {

    @Override
    protected Fragment getFragment() {
        return MainMoreFrag.newInstance();
    }
}
