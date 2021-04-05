package com.ixinrun.lifestyle.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ixinrun.lifestyle.R;
import com.ixinrun.module_common.base.BaseLsAct;
import com.ixinrun.module_common.widget.step.StepCounterService;
import com.ixinrun.module_more.MainMoreFrag;
import com.ixinrun.module_step.MainStepFrag;
import com.ixinrun.module_user.MainUserFrag;

public class MainActivity extends BaseLsAct implements View.OnClickListener {

    private MainMoreFrag mMainMoreFrag;
    private MainStepFrag mMainStepFrag;
    private MainUserFrag mMainUerFrag;
    private FragmentManager mFragMgr;

    private LinearLayout mMainBottomMoreView;
    private ImageView mMainBottomMoreIv;
    private LinearLayout mMainBottomStepView;
    private ImageView mMainBottomStepIv;
    private LinearLayout mMainBottomUserView;
    private ImageView mMainBottomUserIv;

    private static final String MAIN_MORE_FRAG = "MAIN_MORE_FRAG";
    private static final String MAIN_STEP_FRAG = "MAIN_STEP_FRAG";
    private static final String MAIN_USER_FRAG = "MAIN_USER_FRAG";
    private static final String SAVE_STATE = "SAVE_STATE";
    public static final String SELECT_TAB = "SELECT_TAB";

    private int mSaveIndex;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        mMainBottomMoreView = findViewById(R.id.main_bottom_more_view);
        mMainBottomMoreIv = findViewById(R.id.main_bottom_more_iv);
        mMainBottomStepView = findViewById(R.id.main_bottom_step_view);
        mMainBottomStepIv = findViewById(R.id.main_bottom_step_iv);
        mMainBottomUserView = findViewById(R.id.main_bottom_user_view);
        mMainBottomUserIv = findViewById(R.id.main_bottom_user_iv);

        mFragMgr = getSupportFragmentManager();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mMainBottomMoreView.setOnClickListener(this);
        mMainBottomStepView.setOnClickListener(this);
        mMainBottomUserView.setOnClickListener(this);
    }

    @Override
    protected boolean isTranslucentStatusBar() {
        return true;
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            //通过ID或者TAG找到“复活”的fragment
            mMainMoreFrag = (MainMoreFrag) mFragMgr.findFragmentByTag(MAIN_MORE_FRAG);
            mMainStepFrag = (MainStepFrag) mFragMgr.findFragmentByTag(MAIN_STEP_FRAG);
            mMainUerFrag = (MainUserFrag) mFragMgr.findFragmentByTag(MAIN_USER_FRAG);
            //拿到activity结束时保存的状态
            int index = (int) savedInstanceState.get(SAVE_STATE);
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
        FragmentTransaction transaction = mFragMgr.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                mMainBottomMoreIv.setBackgroundResource(R.drawable.main_bottom_function_b);
                if (mMainMoreFrag != null) {
                    transaction.show(mMainMoreFrag);
                } else {
                    mMainMoreFrag = new MainMoreFrag();
                    transaction.add(R.id.main_container_view, mMainMoreFrag, MAIN_MORE_FRAG);
                }
                break;
            case 1:
                mMainBottomStepIv.setBackgroundResource(R.drawable.main_bottom_step_b);
                if (mMainStepFrag != null) {
                    transaction.show(mMainStepFrag);
                } else {
                    mMainStepFrag = new MainStepFrag();
                    transaction.add(R.id.main_container_view, mMainStepFrag, MAIN_STEP_FRAG);
                }
                break;
            case 2:
                mMainBottomUserIv.setBackgroundResource(R.drawable.main_bottom_mycenter_b);
                if (mMainUerFrag != null) {
                    transaction.show(mMainUerFrag);
                } else {
                    mMainUerFrag = new MainUserFrag();
                    transaction.add(R.id.main_container_view, mMainUerFrag, MAIN_USER_FRAG);
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
        mMainBottomMoreIv.setBackgroundResource(R.drawable.main_bottom_function_a);
        mMainBottomStepIv.setBackgroundResource(R.drawable.main_bottom_step_a);
        mMainBottomUserIv.setBackgroundResource(R.drawable.main_bottom_mycenter_a);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mMainMoreFrag != null) {
            transaction.hide(mMainMoreFrag);
        }
        if (mMainStepFrag != null) {
            transaction.hide(mMainStepFrag);
        }
        if (mMainUerFrag != null) {
            transaction.hide(mMainUerFrag);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bottom_more_view:
                setSelectionTab(0);
                break;
            case R.id.main_bottom_step_view:
                setSelectionTab(1);
                break;
            case R.id.main_bottom_user_view:
                setSelectionTab(2);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVE_STATE, mSaveIndex);
        super.onSaveInstanceState(outState);
    }
}
