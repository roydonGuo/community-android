package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.action.StatusAction;
import com.roydon.community.adapter.AppMessageAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.AppMessage;
import com.roydon.community.domain.response.AppMessageListRes;
import com.roydon.community.widget.HintLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppMessageActivity extends BaseActivity implements StatusAction {
    private String TOOL_TITLE = "我的消息";

    // toolbar
    private ImageView ivReturn;
    private TextView tvToolTitle;

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_WHAT_MESSAGE_LIST = 1;

    private HintLayout mHintLayout;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private AppMessageAdapter appMessageAdapter;
    private int pageNum = 1;

    private List<AppMessage> appMessageList = new ArrayList<>();

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
                case HANDLER_WHAT_MESSAGE_LIST:
                    appMessageAdapter.setDatas(appMessageList);
                    appMessageAdapter.notifyDataSetChanged();
                    showComplete();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_app_message;
    }

    @Override
    protected void initView() {
        ivReturn = findViewById(R.id.iv_return);
        tvToolTitle = findViewById(R.id.tv_tool_title);
        tvToolTitle.setText(TOOL_TITLE);

        mHintLayout = findViewById(R.id.hl_status_hint);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        showLoading();
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        appMessageAdapter = new AppMessageAdapter(this);
        recyclerView.setAdapter(appMessageAdapter);
        appMessageAdapter.setOnItemClickListener(new AppMessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getAppMessageList(true);
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            pageNum += 1;
            getAppMessageList(false);
        });
        getAppMessageList(true);
    }

    /**
     * 获取接种记录
     */
    private void getAppMessageList(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE_20);
        Api.build(ApiConfig.APP_MESSAGE_LIST, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("getAppMessageList", res);
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                AppMessageListRes response = gson.fromJson(res, AppMessageListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<AppMessage> resList = response.getData();
                    if (resList != null && resList.size() > 0) {
                        if (isRefresh) {
                            appMessageList = resList;
                        } else {
                            appMessageList.addAll(resList);
                        }
                        mHandler.sendEmptyMessage(HANDLER_WHAT_MESSAGE_LIST);
                    } else {
                        if (isRefresh) {
                            Log.e("getAppMessageList", "暂时无数据");
                            mHandler.sendEmptyMessage(HANDLER_WHAT_EMPTY);
                        } else {
                            Log.e("getAppMessageList", "没有更多数据");
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
                showShortToast("获取失败");
            }
        });
    }

}