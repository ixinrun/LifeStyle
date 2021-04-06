package com.ixinrun.module_user.main;

import androidx.fragment.app.Fragment;

import com.ixinrun.common.test.BaseModuleTestActivity;

public class MainActivity extends BaseModuleTestActivity {

    @Override
    protected Fragment getFragment() {
        return MainUserFrag.newInstance();
    }
}
