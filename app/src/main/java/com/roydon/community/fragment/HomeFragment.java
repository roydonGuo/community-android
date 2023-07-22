package com.roydon.community.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.R;
import com.roydon.community.activity.NewsDetailActivity;
import com.roydon.community.adapter.BannerAdapter;
import com.roydon.community.adapter.NewsHotAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.AppBannerNotice;
import com.roydon.community.domain.response.HotNewsListRes;
import com.roydon.community.domain.response.UserInfoRes;
import com.roydon.community.domain.vo.AppUser;
import com.roydon.community.domain.vo.BannerNoticeListRes;
import com.roydon.community.domain.vo.HotNews;
import com.roydon.community.utils.StringUtil;
import com.roydon.community.view.CircleTransform;
import com.roydon.community.view.SobViewPager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private ViewPager viewPager;
    private RefreshLayout refreshLayout;
    private SobViewPager sobViewPager;
    private ImageView picture, userAvatar;
    private LinearLayout mDotLayout, funcOne;
    private LinearLayoutManager linearLayoutManager;
    private SlidingTabLayout slidingTabLayout;
    private RecyclerView rvNewsHot;

    private BannerAdapter mBannerAdapter;
    private NewsHotAdapter newsHotAdapter;
    private AppUser appUser;
    private List<String> mUrls = new ArrayList<>();
    private List<HotNews> hotNewsList = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mBannerAdapter.setmUrls(mUrls);
                    mBannerAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    newsHotAdapter.setData(hotNewsList);
                    newsHotAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    showUserInfo(appUser);
                    break;
            }
        }
    };

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
//        refreshLayout = mRootView.findViewById(R.id.refreshLayout);
        userAvatar = mRootView.findViewById(R.id.index_user_avatar);
        sobViewPager = mRootView.findViewById(R.id.sob_looper);
        funcOne = mRootView.findViewById(R.id.func_one);
        rvNewsHot = mRootView.findViewById(R.id.rv_news_hot);
    }

    @Override
    protected void initData() {
        // 获取用户信息
        getUserInfo();
        mBannerAdapter = new BannerAdapter(getContext(), mUrls);
        sobViewPager.setAdapter(mBannerAdapter);
        getBannerNoticeList(true);
        // 获取热点新闻List
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNewsHot.setLayoutManager(linearLayoutManager);
        newsHotAdapter = new NewsHotAdapter(getActivity());
        rvNewsHot.setAdapter(newsHotAdapter);
        newsHotAdapter.setOnItemClickListener(new NewsHotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HotNews hotNews = hotNewsList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("newsId", hotNews.getNewsId());
                navigateToWithBundle(NewsDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        getNewsHotList();

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

    }

    /**
     * 轮播图集合
     */
    private void getBannerNoticeList(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", 1);
        params.put("pageSize", 5);
        params.put("showInApp", 1);
        Api.build(ApiConfig.BANNER_NOTICE_LIST, params).getRequestWithToken(getActivity(), new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                BannerNoticeListRes response = new Gson().fromJson(res, BannerNoticeListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<AppBannerNotice> list = response.getRows();
                    if (list != null && list.size() > 0) {
                        if (isRefresh) {
                            mUrls = list.stream().map(AppBannerNotice::getNoticeImgUrl).collect(Collectors.toList());
                            mHandler.sendEmptyMessage(0);
                        }
                    } else {
                        if (isRefresh) {
                            showShortToastSync("暂时无轮播数据");
                        } else {
                            showShortToastSync("没有更多数据");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    /**
     * 热点新闻
     */
    private void getNewsHotList() {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.NEWS_HOT, params).getRequestWithToken(getActivity(), new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                // 后端传递时间格式解析
                HotNewsListRes response = new Gson().fromJson(res, HotNewsListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<HotNews> rows = response.getRows();
                    Log.e("getNewsHotList", rows.toString());
                    // 使用handler将数据传递给主线程
                    hotNewsList = rows;
                    mHandler.sendEmptyMessage(1);
                } else {
                    showShortToastSync("没有热点新闻");
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}