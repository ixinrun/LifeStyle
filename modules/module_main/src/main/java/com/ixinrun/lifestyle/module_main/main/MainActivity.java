package com.ixinrun.lifestyle.module_main.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ixinrun.lifestyle.common.base.BaseLsAct;
import com.ixinrun.lifestyle.common.router.RouterConfig;
import com.ixinrun.lifestyle.common.widget.step.StepCounterService;
import com.ixinrun.lifestyle.module_main.R;


@Route(path = RouterConfig.ModuleMain.MainActivity)
public class MainActivity extends BaseLsAct implements View.OnClickListener {

    private Fragment mMainStepFrag;
    private Fragment mMainEatFrag;
    private Fragment mMainMoreFrag;
    private Fragment mMainUerFrag;
    private FragmentManager mFragMgr;

    private LinearLayout mMainBottomStepView;
    private ImageView mMainBottomStepIv;
    private LinearLayout mMainBottomEatView;
    private ImageView mMainBottomEatIv;
    private LinearLayout mMainBottomMoreView;
    private ImageView mMainBottomMoreIv;
    private LinearLayout mMainBottomUserView;
    private ImageView mMainBottomUserIv;

    private static final String MAIN_STEP_FRAG = "MAIN_STEP_FRAG";
    private static final String MAIN_EAT_FRAG = "MAIN_EAT_FRAG";
    private static final String MAIN_MORE_FRAG = "MAIN_MORE_FRAG";
    private static final String MAIN_USER_FRAG = "MAIN_USER_FRAG";
    private static final String SAVE_STATE = "SAVE_STATE";
    public static final String SELECT_TAB = "SELECT_TAB";

    private int mSaveIndex;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        mMainBottomStepView = findViewById(R.id.main_bottom_step_view);
        mMainBottomStepIv = findViewById(R.id.main_bottom_step_iv);
        mMainBottomEatView = findViewById(R.id.main_bottom_eat_view);
        mMainBottomEatIv = findViewById(R.id.main_bottom_eat_iv);
        mMainBottomMoreView = findViewById(R.id.main_bottom_more_view);
        mMainBottomMoreIv = findViewById(R.id.main_bottom_more_iv);
        mMainBottomUserView = findViewById(R.id.main_bottom_user_view);
        mMainBottomUserIv = findViewById(R.id.main_bottom_user_iv);

        mFragMgr = getSupportFragmentManager();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mMainBottomStepView.setOnClickListener(this);
        mMainBottomEatView.setOnClickListener(this);
        mMainBottomMoreView.setOnClickListener(this);
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
            mMainStepFrag = mFragMgr.findFragmentByTag(MAIN_STEP_FRAG);
            mMainEatFrag = mFragMgr.findFragmentByTag(MAIN_EAT_FRAG);
            mMainMoreFrag = mFragMgr.findFragmentByTag(MAIN_MORE_FRAG);
            mMainUerFrag = mFragMgr.findFragmentByTag(MAIN_USER_FRAG);
            //拿到activity结束时保存的状态
            int index = (int) savedInstanceState.get(SAVE_STATE);
            //恢复Fragment
            setSelectionTab(index);
        } else {
            setSelectionTab(0);
        }

        //开启计步服务
        startService(new Intent(this, StepCounterService.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int index = intent.getIntExtra(SELECT_TAB, 0);
        setSelectionTab(index);
    }

    private void setSelectionTab(int index) {
        cleanSelection();
        //拿到Fragment的事务
        FragmentTransaction transaction = mFragMgr.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                mMainBottomStepIv.setBackgroundResource(R.drawable.main_bottom_step_b);
                if (mMainStepFrag != null) {
                    transaction.show(mMainStepFrag);
                } else {
                    mMainStepFrag = (Fragment) ARouter.getInstance().build(RouterConfig.ModuleStep.MainStepFrag).navigation();
                    transaction.add(R.id.main_container_view, mMainStepFrag, MAIN_STEP_FRAG);
                }
                break;

            case 1:
                mMainBottomEatIv.setBackgroundResource(R.drawable.main_bottom_eat_b);
                if (mMainEatFrag != null) {
                    transaction.show(mMainEatFrag);
                } else {
                    mMainEatFrag = (Fragment) ARouter.getInstance().build(RouterConfig.ModuleEat.MainEatFrag).navigation();
                    transaction.add(R.id.main_container_view, mMainEatFrag, MAIN_EAT_FRAG);
                }
                break;

            case 2:
                mMainBottomMoreIv.setBackgroundResource(R.drawable.main_bottom_function_b);
                if (mMainMoreFrag != null) {
                    transaction.show(mMainMoreFrag);
                } else {
                    mMainMoreFrag = (Fragment) ARouter.getInstance().build(RouterConfig.ModuleMore.MainMoreFrag).navigation();
                    transaction.add(R.id.main_container_view, mMainMoreFrag, MAIN_MORE_FRAG);
                }
                break;

            case 3:
                mMainBottomUserIv.setBackgroundResource(R.drawable.main_bottom_mycenter_b);
                if (mMainUerFrag != null) {
                    transaction.show(mMainUerFrag);
                } else {
                    mMainUerFrag = (Fragment) ARouter.getInstance().build(RouterConfig.ModuleUser.MainUserFrag).navigation();
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
        mMainBottomStepIv.setBackgroundResource(R.drawable.main_bottom_step_a);
        mMainBottomEatIv.setBackgroundResource(R.drawable.main_bottom_eat_a);
        mMainBottomMoreIv.setBackgroundResource(R.drawable.main_bottom_function_a);
        mMainBottomUserIv.setBackgroundResource(R.drawable.main_bottom_mycenter_a);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mMainStepFrag != null) {
            transaction.hide(mMainStepFrag);
        }
        if (mMainEatFrag != null) {
            transaction.hide(mMainEatFrag);
        }
        if (mMainMoreFrag != null) {
            transaction.hide(mMainMoreFrag);
        }
        if (mMainUerFrag != null) {
            transaction.hide(mMainUerFrag);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.main_bottom_step_view) {
            setSelectionTab(0);
        } else if (id == R.id.main_bottom_eat_view) {
            setSelectionTab(1);
        } else if (id == R.id.main_bottom_more_view) {
            setSelectionTab(2);
        } else if (id == R.id.main_bottom_user_view) {
            setSelectionTab(3);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVE_STATE, mSaveIndex);
        super.onSaveInstanceState(outState);
    }
}
