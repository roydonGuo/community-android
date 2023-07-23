package com.roydon.community.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.R;
import com.roydon.community.activity.BDAddressSelectActivity;
import com.roydon.community.activity.LoginActivity;
import com.roydon.community.activity.MyDetailActivity;
import com.roydon.community.activity.UserAddressActivity;
import com.roydon.community.activity.UserOrderActivity;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.response.UserInfoRes;
import com.roydon.community.domain.vo.AppUser;
import com.roydon.community.utils.StringUtil;
import com.roydon.community.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MyFragment extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private LinearLayout mLinearLayout;
    private ImageView userAvatar;
    private TextView userNickName, userDept;
    // 订单栏功能
    private LinearLayout llUserOrder;
    private RelativeLayout rlUserAddress, rlReturnLogin, rlBDAddress;

    private AppUser appUser;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    showUserInfo(appUser);
                    break;
            }
        }
    };

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
        rlBDAddress = mRootView.findViewById(R.id.rl_bd_address);
        rlReturnLogin = mRootView.findViewById(R.id.rl_return_login);
        // 用户info栏
        userAvatar = mRootView.findViewById(R.id.img_header);
        userNickName = mRootView.findViewById(R.id.user_nick_name);
        userDept = mRootView.findViewById(R.id.user_dept);
        // 订单栏功能
        llUserOrder = mRootView.findViewById(R.id.ll_user_order);

        mLinearLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
//                bundle.putString("userId", user.getUserId());
            navigateTo(MyDetailActivity.class);

        });
        rlUserAddress.setOnClickListener(v -> {
            navigateTo(UserAddressActivity.class);
        });
        rlBDAddress.setOnClickListener(v -> {
            navigateTo(BDAddressSelectActivity.class);
        });
        llUserOrder.setOnClickListener(v -> {
            navigateTo(UserOrderActivity.class);
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
        //getUserInfo
        getUserInfo();
    }


    /**
     * 用户信息
     */
    private void getUserInfo() {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.USER_INFO, params).getRequestWithToken(getActivity(), new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("getUserInfo", res);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                UserInfoRes response = gson.fromJson(res, UserInfoRes.class);
                if (response != null && response.getCode() == 200) {
                    AppUser user = response.getData();
                    if (StringUtil.isNotNull(user)) {
                        Log.e("getUserInfo", user.toString());
                        appUser = user;
                        mHandler.sendEmptyMessage(2);
                    } else {
                        showShortToastSync("请重新登陆");
//                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private void showUserInfo(AppUser appUser) {
        if (appUser.getAvatar() != null && !appUser.getAvatar().equals("")) {
            Picasso.with(getContext()).load(appUser.getAvatar()).transform(new CircleTransform()).into(userAvatar);
        }
        userNickName.setText(appUser.getNickName());
        userDept.setText(appUser.getDept().getDeptName());
    }

}