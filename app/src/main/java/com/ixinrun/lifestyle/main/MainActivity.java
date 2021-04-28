package com.ixinrun.lifestyle.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ixinrun.lifestyle.R;
import com.ixinrun.lifestyle.common.base.BaseLsAct;
import com.ixinrun.lifestyle.router.RouterConfig;
import com.ixinrun.lifestyle.widget.MainNavigationBar;

/**
 * 描述: 主页
 * </p>
 *
 * @author ixinrun
 * @date 2020/6/8
 */
public class MainActivity extends BaseLsAct {

    private Fragment mMainStepFrag;
    private Fragment mMainEatFrag;
    private Fragment mMainMoreFrag;
    private Fragment mMainUerFrag;
    private FragmentManager mFragMgr;

    private static final String MAIN_STEP_FRAG = "MAIN_STEP_FRAG";
    private static final String MAIN_EAT_FRAG = "MAIN_EAT_FRAG";
    private static final String MAIN_MORE_FRAG = "MAIN_MORE_FRAG";
    private static final String MAIN_USER_FRAG = "MAIN_USER_FRAG";
    private static final String SAVE_STATE = "SAVE_STATE";
    public static final String SELECT_TAB = "SELECT_TAB";

    private MainNavigationBar mNavBar;

    private int mSaveIndex;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        mNavBar = findViewById(R.id.nav_bar);
        mNavBar.addTab(R.drawable.main_bottom_run_a, R.drawable.main_bottom_run_b, "运动")
                .addTab(R.drawable.main_bottom_eat_a, R.drawable.main_bottom_eat_b, "吃饭")
                .addTab(R.drawable.main_bottom_more_a, R.drawable.main_bottom_more_b, "更多")
                .addTab(R.drawable.main_bottom_user_a, R.drawable.main_bottom_user_b, null);

        mFragMgr = getSupportFragmentManager();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mNavBar.setSwitchCallback(new MainNavigationBar.TabSwitchCallback() {
            @Override
            public void onSwitched(View view, int index) {
                showFrag(index);
            }
        });
    }

    @Override
    protected boolean isTranslucentStatusBar() {
        return true;
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        int index = 0;
        if (savedInstanceState != null) {
            //通过ID或者TAG找到“复活”的fragment
            mMainStepFrag = mFragMgr.findFragmentByTag(MAIN_STEP_FRAG);
            mMainEatFrag = mFragMgr.findFragmentByTag(MAIN_EAT_FRAG);
            mMainMoreFrag = mFragMgr.findFragmentByTag(MAIN_MORE_FRAG);
            mMainUerFrag = mFragMgr.findFragmentByTag(MAIN_USER_FRAG);
            //拿到activity结束时保存的状态
            index = (int) savedInstanceState.get(SAVE_STATE);
        }
        mNavBar.switchTab(index);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int index = intent.getIntExtra(SELECT_TAB, 0);
        showFrag(index);
    }

    private void showFrag(int index) {
        FragmentTransaction transaction = mFragMgr.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (mMainStepFrag != null) {
                    transaction.show(mMainStepFrag);
                } else {
                    mMainStepFrag = (Fragment) ARouter.getInstance().build(RouterConfig.ModuleRun.MainRunFrag).navigation();
                    transaction.add(R.id.main_container_view, mMainStepFrag, MAIN_STEP_FRAG);
                }
                break;

            case 1:
                if (mMainEatFrag != null) {
                    transaction.show(mMainEatFrag);
                } else {
                    mMainEatFrag = (Fragment) ARouter.getInstance().build(RouterConfig.ModuleEat.MainEatFrag).navigation();
                    transaction.add(R.id.main_container_view, mMainEatFrag, MAIN_EAT_FRAG);
                }
                break;

            case 2:
                if (mMainMoreFrag != null) {
                    transaction.show(mMainMoreFrag);
                } else {
                    mMainMoreFrag = (Fragment) ARouter.getInstance().build(RouterConfig.ModuleMore.MainMoreFrag).navigation();
                    transaction.add(R.id.main_container_view, mMainMoreFrag, MAIN_MORE_FRAG);
                }
                break;

            case 3:
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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVE_STATE, mSaveIndex);
        super.onSaveInstanceState(outState);
    }
}
