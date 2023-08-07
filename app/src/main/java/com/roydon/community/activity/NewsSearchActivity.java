package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.adapter.NewsAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.AppNews;
import com.roydon.community.domain.vo.NewsListRes;
import com.roydon.community.utils.string.StringUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsSearchActivity extends BaseActivity {

    // 控件
    private ImageView ivReturn, ivNewsSearch;
    private AutoCompleteTextView queryStr; // 自动完成文本框
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView; // 新闻搜索结果展示
    private ImageView ivSpeech; // 语音输入
    // 数据
    private int pageNum = 1;
    private List<AppNews> newsList = new ArrayList<>();
    // 适配器
    private NewsAdapter newsAdapter;

    private static final int SPEECH_REQUEST_CODE = 100;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    newsAdapter.setDatas(newsList);
                    newsAdapter.notifyDataSetChanged();
                    ProgressBar loadingSpinner = findViewById(R.id.loading_spinner);
                    loadingSpinner.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_news_search;
    }

    @Override
    protected void initView() {
        ivReturn = findViewById(R.id.iv_return);
        queryStr = findViewById(R.id.edit_news_query);
        ivSpeech = findViewById(R.id.iv_speech);
        ivNewsSearch = findViewById(R.id.iv_news_search);

        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        // 先禁用进度条
        ProgressBar loadingSpinner = findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
        // 设置适配器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(this);
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
            selNewsList(true, queryStr.getText().toString());
        });
        refreshLayout.setOnLoadMoreListener((refreshlayout) -> {
            pageNum++;
            selNewsList(false, queryStr.getText().toString());
        });
        // 语音输入
        ivSpeech.setOnClickListener(v -> {
//            displaySpeechRecognizer();
        });

        // 点击搜索
        ivNewsSearch.setOnClickListener(v -> {
            if (StringUtil.isEmpty(queryStr.getText().toString())) {
                // 显示加载动画
                ProgressBar loadingSpinner = findViewById(R.id.loading_spinner);
                loadingSpinner.setVisibility(View.VISIBLE);
            } else {
                // 显示加载动画
                ProgressBar loadingSpinner = findViewById(R.id.loading_spinner);
                loadingSpinner.setVisibility(View.VISIBLE);
                selNewsList(true, queryStr.getText().toString());
            }
        });
        // 软键盘回车监听
        queryStr.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (i == EditorInfo.IME_ACTION_SEND || i == EditorInfo.IME_ACTION_DONE || (keyEvent != null && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode() && KeyEvent.ACTION_DOWN == keyEvent.getAction())) {
                    //处理事件
                    // 显示加载动画
                    ProgressBar loadingSpinner = findViewById(R.id.loading_spinner);
                    loadingSpinner.setVisibility(View.VISIBLE);
                    selNewsList(true, queryStr.getText().toString());
                }
                return false;
            }
        });
    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            Log.e("spokenText", spokenText);
//            queryStr.setText(spokenText);
        }
    }

    private void selNewsList(final boolean isRefresh, String title) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE_20);
        params.put("showInApp", "1");
        params.put("newsTitle", title);
        Api.build(ApiConfig.NEWS_LIST, params).getRequestWithToken(this, new HttpCallback() {
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
//                            showShortToastSync("暂时无数据");
                        } else {
                            Log.e("getNewsList", "没有更多数据");
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
