package com.roydon.community.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.roydon.community.R;
import com.roydon.community.activity.NewsDetailActivity;
import com.roydon.community.adapter.NewsAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.AppNews;
import com.roydon.community.domain.vo.NewsListRes;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsAppFragment extends BaseFragment {

    private String dictValue;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private int pageNum = 1;
    private NewsAdapter newsAdapter;
    private List<AppNews> newsList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    newsAdapter.setDatas(newsList);
                    newsAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public NewsAppFragment() {
    }

    public static NewsAppFragment newInstance(String dictValue) {
        NewsAppFragment fragment = new NewsAppFragment();
        fragment.dictValue = dictValue;
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_news_app;
    }

    @Override
    protected void initView() {
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        refreshLayout = mRootView.findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initData() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(getActivity());
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
                AppNews newsEntity = (AppNews) obj;
                Bundle bundle = new Bundle();
                bundle.putString("newsId", newsEntity.getNewsId());
                navigateToWithBundle(NewsDetailActivity.class, bundle);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getNewsList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener((refreshlayout) -> {
            pageNum++;
            getNewsList(false);
        });
        getNewsList(true);
    }

    private void getNewsList(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE);
        params.put("newsType", dictValue);
        params.put("showInApp", 1);
        Api.build(ApiConfig.NEWS_LIST, params).getRequestWithToken(getActivity(), new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                NewsListRes response = new Gson().fromJson(res, NewsListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<AppNews> list = response.getRows();
                    if (list != null && list.size() > 0) {
                        if (isRefresh) {
                            newsList = list;
                        } else {
                            newsList.addAll(list);
                        }
                        mHandler.sendEmptyMessage(0);
                    } else {
                        if (isRefresh) {
                            Log.e("getNewsList", "暂时无数据");
                            showShortToastSync("暂时无数据");
                        } else {
                            Log.e("getNewsList", "没有更多数据");
                            showShortToastSync("没有更多数据");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
            }
        });
    }
}