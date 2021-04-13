package com.ixinrun.lifestyle.module_user.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ixinrun.base.img.ImageLoaderMgr;
import com.ixinrun.lifestyle.common.base.BaseLsFrag;
import com.ixinrun.lifestyle.common.router.RouterConfig;
import com.ixinrun.lifestyle.module_user.R;
import com.ixinrun.lifestyle.module_user.setting.SettingActivity;

@Route(path = RouterConfig.ModuleUser.MainUserFrag)
public class MainUserFrag extends BaseLsFrag {

    private ImageView mUserTopIv;
    private ImageView mUserHeadIv;
    private TextView mUserNameTv;
    private TextView mUserAphorismTv;
    private ImageView mSettingIv;

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.main_user_frag_layout, null);
        mUserTopIv = view.findViewById(R.id.user_top_iv);
        mUserHeadIv = view.findViewById(R.id.user_head_iv);
        mUserNameTv = view.findViewById(R.id.user_name_tv);
        mUserAphorismTv = view.findViewById(R.id.user_aphorism_tv);
        mSettingIv = view.findViewById(R.id.setting_iv);

        return view;
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {
        mSettingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.startActivity(mContext);
            }
        });

        String imgUrl = "https://api.kdcc.cn/img/";
        ImageLoaderMgr.getInstance()
                .builder()
                .thumbnail(0.1f)
                .build()
                .load(imgUrl, mUserTopIv);

    }

    public static MainUserFrag newInstance() {
        Bundle args = new Bundle();

        MainUserFrag fragment = new MainUserFrag();
        fragment.setArguments(args);
        return fragment;
    }
}
