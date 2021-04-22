package com.ixinrun.lifestyle.module_eat.main;

import androidx.fragment.app.Fragment;

import com.ixinrun.lifestyle.common.debug.BaseModuleDebugActivity;

public class MainActivity extends BaseModuleDebugActivity {

    @Override
    protected Fragment getFragment() {
        return MainEatFrag.newInstance();
    }
}
