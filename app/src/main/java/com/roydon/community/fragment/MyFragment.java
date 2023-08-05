package com.roydon.community.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.R;
import com.roydon.community.activity.AccessRecordActivity;
import com.roydon.community.activity.BDAddressSelectActivity;
import com.roydon.community.activity.LoginActivity;
import com.roydon.community.activity.MessageActivity;
import com.roydon.community.activity.SettingActivity;
import com.roydon.community.activity.UserAddressActivity;
import com.roydon.community.activity.UserInfoActivity;
import com.roydon.community.activity.UserOrderActivity;
import com.roydon.community.activity.WebviewActivity;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.constants.CacheConstants;
import com.roydon.community.constants.Constants;
import com.roydon.community.domain.response.UserInfoRes;
import com.roydon.community.domain.vo.AppUser;
import com.roydon.community.enums.ThemeEnum;
import com.roydon.community.listener.OnShareDialogClickListener;
import com.roydon.community.utils.android.SystemUtils;
import com.roydon.community.utils.cache.SPUtils;
import com.roydon.community.utils.string.StringUtil;
import com.roydon.community.view.AlertDialogX;
import com.roydon.community.view.CircleTransform;
import com.roydon.community.view.DialogX;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MyFragment extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private LinearLayout mLinearLayout;
    private ImageView userBg, userAvatar;
    private TextView userNickName, userDept;
    // top-bar
    private ImageView ivShare, ivTheme;
    // 订单栏功能
    private LinearLayout llUserOrder;
    private RelativeLayout rlUserAddress, rlAccessRecord, rlReturnLogin, rlWebview, rlSetting;

    // 测试功能区
    private RelativeLayout rlBDAddress, rlMessage;

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
        ivTheme = mRootView.findViewById(R.id.iv_theme);

        mLinearLayout = mRootView.findViewById(R.id.layout_my_detail);
        rlUserAddress = mRootView.findViewById(R.id.rl_user_address);
        rlAccessRecord = mRootView.findViewById(R.id.rl_access_record);
        rlReturnLogin = mRootView.findViewById(R.id.rl_return_login);
        // 用户info栏
//        userBg = mRootView.findViewById(R.id.iv_user_bg);
        userAvatar = mRootView.findViewById(R.id.img_header);
        userNickName = mRootView.findViewById(R.id.user_nick_name);
        userDept = mRootView.findViewById(R.id.user_dept);
        // 订单栏功能
        llUserOrder = mRootView.findViewById(R.id.ll_user_order);

        // 功能测试区
        rlBDAddress = mRootView.findViewById(R.id.rl_bd_address);
        rlMessage = mRootView.findViewById(R.id.rl_message);
        rlWebview = mRootView.findViewById(R.id.rl_webview);

        // 设置
        rlSetting = mRootView.findViewById(R.id.rl_setting);

        // 分享
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
//        userBg.setOnClickListener(v -> {
//            v.getLayoutParams().height = 164 * 2;
//        });
        // 昼夜切换
        ivTheme.setOnClickListener(v -> {
            int current = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (current == Configuration.UI_MODE_NIGHT_YES) {
                ((AppCompatActivity) getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SPUtils.setTheme("theme", ThemeEnum.SUN.getCode(), getContext());
            } else {
                ((AppCompatActivity) getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                SPUtils.setTheme("theme", ThemeEnum.MOON.getCode(), getContext());
            }
        });
        mLinearLayout.setOnClickListener(v -> {
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
        rlSetting.setOnClickListener(v -> {
            navigateTo(SettingActivity.class);
        });
        rlMessage.setOnClickListener(v -> {
            navigateTo(MessageActivity.class);
        });
        rlWebview.setOnClickListener(v -> {
            navigateTo(WebviewActivity.class);
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
                    SharedPreferences sp = getContext().getSharedPreferences(Constants.AUTHORIZATION, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.apply();
                    // 清空缓存
                    SPUtils.remove(CacheConstants.USERID, getContext());
                    SPUtils.remove(CacheConstants.USERNAME, getContext());
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
            Picasso.with(getContext()).load(appUser.getAvatar()).transform(new CircleTransform()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)//限制Picasso从内存中加载图片  不然头像更换 不及时
                    .into(userAvatar);
        }
        userNickName.setText(appUser.getNickName());
        userDept.setText(appUser.getDept().getDeptName());
    }

}