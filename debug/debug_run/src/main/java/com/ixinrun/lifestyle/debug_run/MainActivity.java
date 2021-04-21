package com.ixinrun.lifestyle.debug_run;

import androidx.fragment.app.Fragment;

import com.ixinrun.lifestyle.common.test.BaseModuleTestActivity;
import com.ixinrun.lifestyle.module_run.main.MainRunFrag;

public class MainActivity extends BaseModuleTestActivity {

    @Override
    protected Fragment getFragment() {
        return MainRunFrag.newInstance();
    }
}
