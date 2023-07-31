package com.roydon.community.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
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
import com.roydon.community.activity.AccessRecordActivity;
import com.roydon.community.activity.BDAddressSelectActivity;
import com.roydon.community.activity.LoginActivity;
import com.roydon.community.activity.UserAddressActivity;
import com.roydon.community.activity.UserInfoActivity;
import com.roydon.community.activity.UserOrderActivity;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.response.UserInfoRes;
import com.roydon.community.domain.vo.AppUser;
import com.roydon.community.listener.OnShareDialogClickListener;
import com.roydon.community.utils.android.SystemUtils;
import com.roydon.community.utils.string.StringUtil;
import com.roydon.community.view.AlertDialogX;
import com.roydon.community.view.CircleTransform;
import com.roydon.community.view.DialogX;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MyFragment extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private LinearLayout mLinearLayout;
    private ImageView ivShare, userAvatar;
    private TextView userNickName, userDept;
    // 订单栏功能
    private LinearLayout llUserOrder;
    private RelativeLayout rlUserAddress, rlAccessRecord, rlReturnLogin, rlBDAddress;

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
        // 顶部功能icon
        ivShare = mRootView.findViewById(R.id.iv_share);
        mLinearLayout = mRootView.findViewById(R.id.layout_my_detail);
        rlUserAddress = mRootView.findViewById(R.id.rl_user_address);
        rlBDAddress = mRootView.findViewById(R.id.rl_bd_address);
        rlAccessRecord = mRootView.findViewById(R.id.rl_access_record);
        rlReturnLogin = mRootView.findViewById(R.id.rl_return_login);
        // 用户info栏
        userAvatar = mRootView.findViewById(R.id.img_header);
        userNickName = mRootView.findViewById(R.id.user_nick_name);
        userDept = mRootView.findViewById(R.id.user_dept);
        // 订单栏功能
        llUserOrder = mRootView.findViewById(R.id.ll_user_order);

        ivShare.setOnClickListener(v -> {
            DialogX.showShareDialog(getContext(), new OnShareDialogClickListener() {
                @Override
                public void onShareToWechat() {
                    SystemUtils.copyToClipboard(getContext(), ApiConfig.BASE_URl);
                    showShortToast("Copied");
                }

                @Override
                public void onShareToPengyouquan() {
                    showShortToast("onShareToPengyouquan");
                }

                @Override
                public void onShareToQQ() {
                    showShortToast("onShareToQQ");
                }

                @Override
                public void onShareToWeibo() {
                    showShortToast("onShareToWeibo");
                }

                @Override
                public void onCancelShare() {

                }
            });
        });

        mLinearLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
//                bundle.putString("userId", user.getUserId());
            navigateTo(UserInfoActivity.class);

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

        //出入社区报备
        rlAccessRecord.setOnClickListener(v -> {
            navigateTo(AccessRecordActivity.class);
        });
        // 退出登录
        rlReturnLogin.setOnClickListener(v -> {
            String title = "退出登录";
            String msg = "确定清除数据并退出登录吗？";
            AlertDialogX.showCustomAlertDialog(getContext(), title, msg, new View.OnClickListener() {
                @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
                @Override
                public void onClick(View view) {
                    SharedPreferences sp = getContext().getSharedPreferences("sp_roydon", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.apply();
                    navigateToWithFlag(LoginActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
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