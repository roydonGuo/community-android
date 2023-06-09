package com.roydon.community.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.roydon.community.R;
import com.roydon.community.adapter.HomeAdapter;
import com.roydon.community.adapter.NewsAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.entity.AppNews;
import com.roydon.community.entity.NewsCategoryRes;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsFragment extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private NewsAdapter newsAdapter;
    private LinearLayoutManager linearLayoutManager;

    private Long dictCode;
    private int pageNum = 1;
    private List<AppNews> newsList = new ArrayList<>();

    public static NewsFragment newInstance(Long dictCode) {
        NewsFragment fragment = new NewsFragment();
        fragment.dictCode = dictCode;
        return fragment;
    }

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        // 绑定fixedViewPager待渲染数据
        viewPager = mRootView.findViewById(R.id.fixedViewPager);
        // 绑定顶部分类导航
        slidingTabLayout = mRootView.findViewById(R.id.slidingTabLayout);
    }

    @Override
    protected void initData() {
        this.getNewsCategoryList();
//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        newsAdapter = new NewsAdapter(getActivity());
//        recyclerView.setAdapter(newsAdapter);
//        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Serializable obj) {
////                showToast("点击");
//                AppNews newsEntity = (AppNews) obj;
//                String url = "http://192.168.31.32:8089/newsDetail?title=" + newsEntity.getAuthorName();
//                Bundle bundle = new Bundle();
//                bundle.putString("url", url);
//                navigateToWithBundle(WebActivity.class, bundle);
//            }
//        });
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                pageNum = 1;
//                getNewsList(true);
//            }
//        });
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshlayout) {
//                pageNum++;
//                getNewsList(false);
//            }
//        });
//        getNewsList(true);
    }
//    private void getNewsList(final boolean isRefresh){
//        HashMap<String, Object> params = new HashMap<>();
//        params.put("page", pageNum);
//        params.put("limit", ApiConfig.PAGE_SIZE);
//        Api.config(ApiConfig.NEWS_LIST, params).getRequest(getActivity(), new TtitCallback() {
//            @Override
//            public void onSuccess(final String res) {
//                if (isRefresh) {
//                    refreshLayout.finishRefresh(true);
//                } else {
//                    refreshLayout.finishLoadMore(true);
//                }
//                NewsListResponse response = new Gson().fromJson(res, NewsListResponse.class);
//                if (response != null && response.getCode() == 0) {
//                    List<NewsEntity> list = response.getPage().getList();
//                    if (list != null && list.size() > 0) {
//                        if (isRefresh) {
//                            datas = list;
//                        } else {
//                            datas.addAll(list);
//                        }
//                        mHandler.sendEmptyMessage(0);
//                    } else {
//                        if (isRefresh) {
//                            showToastSync("暂时无数据");
//                        } else {
//                            showToastSync("没有更多数据");
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                if (isRefresh) {
//                    refreshLayout.finishRefresh(true);
//                } else {
//                    refreshLayout.finishLoadMore(true);
//                }
//            }
//        });
//    }

    /**
     * 新闻分类集合
     */
    private void getNewsCategoryList() {
        HashMap<String, Object> params = new HashMap<>();
        Api.config(ApiConfig.NEWS_CATEGORY_LIST, params).getRequest(getActivity(), new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NewsCategoryRes response = new Gson().fromJson(res, NewsCategoryRes.class);
                        if (response != null && response.getCode() == 200) {
                            List<NewsCategoryRes.DataBean> data = response.getData();
                            if (data != null && data.size() > 0) {
                                mTitles = new String[data.size()];
                                for (int i = 0; i < data.size(); i++) {
                                    mTitles[i] = data.get(i).getDictLabel();
                                    mFragments.add(NewsFragment.newInstance(data.get(i).getDictCode()));
                                }
                                viewPager.setOffscreenPageLimit(mFragments.size());
                                viewPager.setAdapter(new HomeAdapter(getFragmentManager(), mTitles, mFragments));
                                slidingTabLayout.setViewPager(viewPager);
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }
}