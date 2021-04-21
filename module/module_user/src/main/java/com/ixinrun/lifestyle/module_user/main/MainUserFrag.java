package com.ixinrun.lifestyle.module_user.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ixinrun.base.img.ImageLoaderMgr;
import com.ixinrun.base.utils.DateUtil;
import com.ixinrun.lifestyle.common.base.BaseLsFrag;
import com.ixinrun.lifestyle.common.data.UserInfoBean;
import com.ixinrun.lifestyle.common.mgr.StorageMgr;
import com.ixinrun.lifestyle.common.router.RouterConfig;
import com.ixinrun.lifestyle.module_user.R;
import com.ixinrun.lifestyle.module_user.setting.SettingActivity;

@Route(path = RouterConfig.ModuleUser.MainUserFrag)
public class MainUserFrag extends BaseLsFrag {

    private ImageView mUserPhotoIv;
    private ImageView mUserHeadIv;
    private TextView mUserNameTv;
    private TextView mUserMottoTv;
    private ImageView mSettingIv;
    private RelativeLayout mUserWeightRl;
    private TextView mUserWeightTv;
    private RelativeLayout mUserHeightRl;
    private TextView mUserHeightTv;

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.main_user_frag_layout, null);
        mUserPhotoIv = view.findViewById(R.id.user_photo_iv);
        mUserHeadIv = view.findViewById(R.id.user_head_iv);
        mUserNameTv = view.findViewById(R.id.user_name_tv);
        mUserMottoTv = view.findViewById(R.id.user_motto_tv);
        mSettingIv = view.findViewById(R.id.setting_iv);
        mUserWeightRl = view.findViewById(R.id.user_weight_rl);
        mUserWeightTv = view.findViewById(R.id.user_weight_tv);
        mUserHeightRl = view.findViewById(R.id.user_height_rl);
        mUserHeightTv = view.findViewById(R.id.user_height_tv);

        return view;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSettingIv.setOnClickListener(v -> SettingActivity.startActivity(mContext));
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {
        UserInfoBean userInfo = StorageMgr.User.getUserInfo();
        if (userInfo == null){
            return;
        }
        ImageLoaderMgr.getInstance()
                .builder()
                .signature(DateUtil.getCurrent(DateUtil.FORMAT_YMD))
                .build()
                .load(userInfo.getPhoto(), mUserPhotoIv);
        ImageLoaderMgr.getInstance().load(userInfo.getHead(), mUserHeadIv);
        mUserNameTv.setText(userInfo.getUserName());
        mUserMottoTv.setText(userInfo.getMotto());
        mUserWeightTv.setText(userInfo.getWeight() + " kg");
        mUserHeightTv.setText(userInfo.getHeight() + " cm");
    }

    public static MainUserFrag newInstance() {
        Bundle args = new Bundle();

        MainUserFrag fragment = new MainUserFrag();
        fragment.setArguments(args);
        return fragment;
    }
}
