package com.roydon.community.fragment;

import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.roydon.community.R;
import com.roydon.community.adapter.BannerAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.AppBannerNotice;
import com.roydon.community.domain.vo.BannerNoticeListRes;
import com.roydon.community.view.SobViewPager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
    private LinearLayout mDotLayout;
    private SlidingTabLayout slidingTabLayout;

    private BannerAdapter mBannerAdapter;
    private List<String> mUrls = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mBannerAdapter.setmUrls(mUrls);
                    mBannerAdapter.notifyDataSetChanged();
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
        refreshLayout = mRootView.findViewById(R.id.refreshLayout);
        sobViewPager = mRootView.findViewById(R.id.sob_looper);
        mDotLayout = mRootView.findViewById(R.id.dot_layout);
    }

    @Override
    protected void initData() {

        mBannerAdapter = new BannerAdapter(getContext(), mUrls);
        sobViewPager.setAdapter(mBannerAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getBannerNoticeList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                getBannerNoticeList(false);
            }
        });
        getBannerNoticeList(true);

    }

    /**
     * 集合
     */
    private void getBannerNoticeList(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", 1);
        params.put("pageSize", 5);
        params.put("showInApp", 1);
        Api.build(ApiConfig.BANNER_NOTICE_LIST, params).getRequestWithToken(getActivity(), new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                BannerNoticeListRes response = new Gson().fromJson(res, BannerNoticeListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<AppBannerNotice> list = response.getRows();
                    if (list != null && list.size() > 0) {
                        if (isRefresh) {
                            mUrls = list.stream().map(AppBannerNotice::getNoticeImgUrl).collect(Collectors.toList());
                        } else {
                            mUrls.addAll(list.stream().map(AppBannerNotice::getNoticeImgUrl).collect(Collectors.toList()));
                        }
                        mHandler.sendEmptyMessage(0);
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

}