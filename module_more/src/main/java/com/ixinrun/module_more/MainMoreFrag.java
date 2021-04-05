package com.ixinrun.module_more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ixinrun.module_common.base.BaseLsFrag;

public class MainMoreFrag extends BaseLsFrag {
    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.main_more_frag_layout, null);
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    public static MainMoreFrag newInstance() {
        Bundle args = new Bundle();

        MainMoreFrag fragment = new MainMoreFrag();
        fragment.setArguments(args);
        return fragment;
    }
}
