package com.roydon.community.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.roydon.community.R;
import com.roydon.community.activity.LoginActivity;
import com.roydon.community.activity.MyDetailActivity;
import com.roydon.community.activity.UserAddressActivity;

import java.util.ArrayList;

public class MyFragment extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private LinearLayout mLinearLayout;
    private RelativeLayout rlUserAddress, rlReturnLogin;

    public MyFragment() {
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {
        mLinearLayout = mRootView.findViewById(R.id.layout_my_detail);
        rlUserAddress = mRootView.findViewById(R.id.rl_user_address);
        rlReturnLogin = mRootView.findViewById(R.id.rl_return_login);
        mLinearLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
//                bundle.putString("userId", user.getUserId());
            navigateTo(MyDetailActivity.class);

        });
        rlUserAddress.setOnClickListener(v -> {
            navigateTo(UserAddressActivity.class);
        });
//        viewPager = mRootView.findViewById(R.id.fixedViewPager);
//        slidingTabLayout = mRootView.findViewById(R.id.slidingTabLayout);
    }

    @Override
    protected void initData() {
        // 退出登录
        rlReturnLogin.setOnClickListener(v -> {
            SharedPreferences sp = getContext().getSharedPreferences("sp_roydon", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.apply();
            navigateToWithFlag(LoginActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        });
    }

}