package com.ixinrun.lifestyle.debug_eat;

import androidx.fragment.app.Fragment;

import com.ixinrun.lifestyle.common.debug.BaseModuleDebugActivity;
import com.ixinrun.lifestyle.module_eat.main.MainEatFrag;

public class MainActivity extends BaseModuleDebugActivity {

    @Override
    protected Fragment getFragment() {
        return MainEatFrag.newInstance();
    }
}
