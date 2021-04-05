package com.ixinrun.module_step;

import androidx.fragment.app.Fragment;

import com.ixinrun.module_common.test.BaseModuleTestActivity;

public class MainActivity extends BaseModuleTestActivity {

    @Override
    protected Fragment getFragment() {
        return MainStepFrag.newInstance();
    }
}
