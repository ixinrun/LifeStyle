package com.ixinrun.lifestyle.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ixinrun.app_base.activity.BaseActivity;
import com.ixinrun.lifestyle.R;
import com.ixinrun.lifestyle.ui.fragment.FunctionsFragment;
import com.ixinrun.lifestyle.ui.fragment.MyCenterFragment;
import com.ixinrun.lifestyle.ui.fragment.StepMainFragment;
import com.ixinrun.lifestyle.widget.step.StepCounterService;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FunctionsFragment mFunctionsFragment;
    private StepMainFragment mStepMainFragment;
    private MyCenterFragment mMyCenterFragment;
    private FragmentManager fragmentManager;

    private LinearLayout main_bottom_function_view;
    private ImageView main_bottom_function_iv;
    private LinearLayout main_bottom_step_view;
    private ImageView main_bottom_step_iv;
    private LinearLayout main_bottom_setting_view;
    private ImageView main_bottom_setting_iv;

    private static final String FUNCTION_FRAGMENT = "FUNCTION_FRAGMENT";
    private static final String STEPMAIN_FRAGMENT = "STEPMAIN_FRAGMENT";
    private static final String STEPSETTING_FRAGMENT = "STEPSETTING_FRAGMENT";
    private static final String SAVEINSTANCESTATE = "SAVEINSTANCESTATE";
    public static final String SELECT_TAB = "SELECT_TAB";

    private int mSaveIndex;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        main_bottom_function_view = findViewById(R.id.main_bottom_function_view);
        main_bottom_function_iv = findViewById(R.id.main_bottom_function_iv);
        main_bottom_step_view = findViewById(R.id.main_bottom_step_view);
        main_bottom_step_iv = findViewById(R.id.main_bottom_step_iv);
        main_bottom_setting_view = findViewById(R.id.main_bottom_setting_view);
        main_bottom_setting_iv = findViewById(R.id.main_bottom_setting_iv);

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void initEventHandlers() {
        super.initEventHandlers();
        main_bottom_function_view.setOnClickListener(this);

        main_bottom_step_view.setOnClickListener(this);
        main_bottom_setting_view.setOnClickListener(this);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            //通过ID或者TAG找到“复活”的fragment
            mFunctionsFragment = (FunctionsFragment) fragmentManager.findFragmentByTag(FUNCTION_FRAGMENT);
            mStepMainFragment = (StepMainFragment) fragmentManager.findFragmentByTag(STEPMAIN_FRAGMENT);
            mMyCenterFragment = (MyCenterFragment) fragmentManager.findFragmentByTag(STEPSETTING_FRAGMENT);
            //拿到activity结束时保存的状态
            int index = (int) savedInstanceState.get(SAVEINSTANCESTATE);
            //恢复Fragment
            setSelectionTab(index);
        } else {
            setSelectionTab(1);
        }

        //开启计步服务
        startService(new Intent(this, StepCounterService.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int index = intent.getIntExtra(SELECT_TAB, 1);
        setSelectionTab(index);
    }

    private void setSelectionTab(int index) {
        cleanSelection();
        //拿到Fragment的事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                main_bottom_function_iv.setBackgroundResource(R.drawable.main_bottom_function_b);
                if (mFunctionsFragment != null) {
                    transaction.show(mFunctionsFragment);
                } else {
                    mFunctionsFragment = new FunctionsFragment();
                    transaction.add(R.id.main_container_view, mFunctionsFragment, FUNCTION_FRAGMENT);
                }
                break;
            case 1:
                main_bottom_step_iv.setBackgroundResource(R.drawable.main_bottom_step_b);
                if (mStepMainFragment != null) {
                    transaction.show(mStepMainFragment);
                } else {
                    mStepMainFragment = new StepMainFragment();
                    transaction.add(R.id.main_container_view, mStepMainFragment, STEPMAIN_FRAGMENT);
                }
                break;
            case 2:
                main_bottom_setting_iv.setBackgroundResource(R.drawable.main_bottom_mycenter_b);
                if (mMyCenterFragment != null) {
                    transaction.show(mMyCenterFragment);
                } else {
                    mMyCenterFragment = new MyCenterFragment();
                    transaction.add(R.id.main_container_view, mMyCenterFragment, STEPSETTING_FRAGMENT);
                }
                break;
            default:
                break;
        }
        //执行事务
        transaction.commit();
        mSaveIndex = index;
    }

    private void cleanSelection() {
        main_bottom_function_iv.setBackgroundResource(R.drawable.main_bottom_function_a);
        main_bottom_step_iv.setBackgroundResource(R.drawable.main_bottom_step_a);
        main_bottom_setting_iv.setBackgroundResource(R.drawable.main_bottom_mycenter_a);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mFunctionsFragment != null) {
            transaction.hide(mFunctionsFragment);
        }
        if (mStepMainFragment != null) {
            transaction.hide(mStepMainFragment);
        }
        if (mMyCenterFragment != null) {
            transaction.hide(mMyCenterFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bottom_function_view:
                setSelectionTab(0);
                break;
            case R.id.main_bottom_step_view:
                setSelectionTab(1);
                break;
            case R.id.main_bottom_setting_view:
                setSelectionTab(2);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVEINSTANCESTATE, mSaveIndex);
        super.onSaveInstanceState(outState);
    }
}
