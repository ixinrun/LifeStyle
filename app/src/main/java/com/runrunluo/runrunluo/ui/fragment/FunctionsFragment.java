package com.runrunluo.runrunluo.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runrunluo.runrunluo.R;

/**
 * Created by BigRun on 2016/9/2.
 */
public class FunctionsFragment extends Fragment {

    private Activity mActivity;
    private View mContentView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_functions,null);

        return mContentView;
    }
}
