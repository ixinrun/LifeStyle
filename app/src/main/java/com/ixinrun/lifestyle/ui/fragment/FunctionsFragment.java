package com.ixinrun.lifestyle.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ixinrun.lifestyle.R;
import com.ixinrun.lifestyle.base.BaseFragment;

public class FunctionsFragment extends BaseFragment {

    private Activity mActivity;
    private View mContentView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_functions, null);

        return mContentView;
    }
}
