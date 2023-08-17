package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.action.StatusAction;
import com.roydon.community.adapter.UserOrderAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.vo.MallOrderVO;
import com.roydon.community.domain.vo.OrderListRes;
import com.roydon.community.widget.HintLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserOrderActivity extends BaseActivity implements StatusAction {
    private String TOOLBAR_TITLE = "我的订单";

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_REFRESH_ORDER_LIST = 1;

    private RefreshLayout refreshLayout;
    private RecyclerView rvUserOrder;
    private int pageNum = 1;
    private UserOrderAdapter userOrderAdapter;
    private List<MallOrderVO> userOrderList = new ArrayList<>();

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
                    showLayout(ContextCompat.getDrawable(UserOrderActivity.this, R.mipmap.icon_hint_order), "暂无订单", null);
                    break;
                case HANDLER_REFRESH_ORDER_LIST:
                    userOrderAdapter.setDatas(userOrderList);
                    userOrderAdapter.notifyDataSetChanged();
                    showComplete();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_user_order;
    }

    @Override
    protected void initView() {
        initToolBar(TOOLBAR_TITLE);
        mHintLayout = findViewById(R.id.hintLayout);
        // 刷新组件
        refreshLayout = findViewById(R.id.refreshLayout);
        // RecyclerView
        rvUserOrder = findViewById(R.id.rv_user_order);
        // 显示加载动画
        showLoading();

    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserOrderActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvUserOrder.setLayoutManager(linearLayoutManager);
        userOrderAdapter = new UserOrderAdapter(this);
        rvUserOrder.setAdapter(userOrderAdapter);

        userOrderAdapter.setOnItemClickListener(new UserOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showShortToast(position + "");
            }

            @Override
            public void onItemLongClick(View view, int position) {
                showShortToast(position + "");

            }
        });

        refreshLayout.setOnRefreshListener(r -> {
            pageNum = 1;
            getUserOrderList(true);
        });
        refreshLayout.setOnLoadMoreListener(r -> {
            pageNum++;
            getUserOrderList(false);
        });
        getUserOrderList(true);
    }

    /**
     * 获取用户订单
     *
     * @param isRefresh
     */
    private void getUserOrderList(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE);
        Api.build(ApiConfig.MALL_USER_ORDER_LIST, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("getUserOrderList", res);
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                // 后端传递时间格式解析
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                OrderListRes response = gson.fromJson(res, OrderListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<MallOrderVO> data = response.getData();
                    if (data != null && data.size() > 0) {
                        if (isRefresh) {
                            userOrderList = data;
                        } else {
                            userOrderList.addAll(data);
                        }
                        mHandler.sendEmptyMessage(HANDLER_REFRESH_ORDER_LIST);
                    } else {
                        if (isRefresh) {
//                            showSyncShortToast("暂无数据");
                            mHandler.sendEmptyMessage(HANDLER_WHAT_EMPTY);
                        } else {
//                            showSyncShortToast("没有更多数据");
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