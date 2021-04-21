package com.ixinrun.lifestyle.module_user.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ixinrun.lifestyle.common.base.BaseLsAct;
import com.ixinrun.lifestyle.module_user.R;

public class SettingActivity extends BaseLsAct {
    @Override
    protected void initView() {
        setContentView(R.layout.setting_activity);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }
}
