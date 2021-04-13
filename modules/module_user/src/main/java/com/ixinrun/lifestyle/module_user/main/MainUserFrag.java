package com.ixinrun.lifestyle.module_user.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    protected void initEvent() {
        super.initEvent();
        mSettingIv.setOnClickListener(v -> SettingActivity.startActivity(mContext));
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {
        String topImgUrl = "https://api.kdcc.cn/img/";
        ImageLoaderMgr.getInstance().load(topImgUrl, mUserTopIv);

        String headerImgUrl = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201512%2F05%2F20151205155108_tXrxZ.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620908007&t=aa9b1e9c4466076022aa492c5ad91e8e";
        ImageLoaderMgr.getInstance().load(headerImgUrl, mUserHeadIv);
    }

    public static MainUserFrag newInstance() {
        Bundle args = new Bundle();

        MainUserFrag fragment = new MainUserFrag();
        fragment.setArguments(args);
        return fragment;
    }
}
