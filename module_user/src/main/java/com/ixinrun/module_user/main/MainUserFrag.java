package com.ixinrun.module_user.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ixinrun.common.base.BaseLsFrag;
import com.ixinrun.module_user.R;

public class MainUserFrag extends BaseLsFrag {

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.main_user_frag_layout, null);
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    public static MainUserFrag newInstance() {
        Bundle args = new Bundle();

        MainUserFrag fragment = new MainUserFrag();
        fragment.setArguments(args);
        return fragment;
    }
}
