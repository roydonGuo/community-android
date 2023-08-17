package com.roydon.community.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.roydon.community.R;
import com.roydon.community.action.StatusAction;
import com.roydon.community.activity.NewsDetailActivity;
import com.roydon.community.adapter.NewsAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.AppNews;
import com.roydon.community.domain.vo.NewsListRes;
import com.roydon.community.widget.HintLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsAppFragment extends BaseLazyLoadFragment implements StatusAction {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_WHAT_NEWS_LIST = 1;

    private String dictValue;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private int pageNum = 1;
    private NewsAdapter newsAdapter;
    private List<AppNews> newsList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    private HintLayout mHintLayout;

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_WHAT_EMPTY:
                    showEmpty();
                    break;
                case HANDLER_WHAT_NEWS_LIST:
                    newsAdapter.setDatas(newsList);
                    newsAdapter.notifyDataSetChanged();
                    showComplete();
                    break;
                default:
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
    protected void lazyLoad() {
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
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getNewsList(true);
        });
        refreshLayout.setOnLoadMoreListener((refreshlayout) -> {
            pageNum++;
            getNewsList(false);
        });
        getNewsList(true);
    }

    @Override
    protected void visibleReLoad() {

    }

    @Override
    protected void inVisibleRelease() {

    }

    @Override
    protected void resume() {

    }

    @Override
    protected void pause() {

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_news_app;
    }

    @Override
    protected void initView() {
        mHintLayout = mRootView.findViewById(R.id.hintLayout);
        refreshLayout = mRootView.findViewById(R.id.refreshLayout);
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        // 显示加载动画
        showLoading();
    }

    @Override
    protected void initData() {

    }

    private void getNewsList(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE);
        params.put("newsType", dictValue);
        params.put("showInApp", "1");
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
                        mHandler.sendEmptyMessage(HANDLER_WHAT_NEWS_LIST);
                    } else {
                        if (isRefresh) {
                            Log.e("getNewsList", "暂时无数据");
                            mHandler.sendEmptyMessage(HANDLER_WHAT_EMPTY);
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
                mHandler.sendEmptyMessage(HANDLER_WHAT_EMPTY);
            }
        });
    }
}