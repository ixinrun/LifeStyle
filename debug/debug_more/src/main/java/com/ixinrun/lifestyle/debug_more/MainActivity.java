package com.ixinrun.lifestyle.debug_more;

import androidx.fragment.app.Fragment;

import com.ixinrun.lifestyle.common.debug.BaseModuleDebugActivity;
import com.ixinrun.lifestyle.module_more.main.MainMoreFrag;

public class MainActivity extends BaseModuleDebugActivity {

    @Override
    protected Fragment getFragment() {
        return MainMoreFrag.newInstance();
    }
}
