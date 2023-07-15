package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.adapter.UserOrderAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.vo.MallOrderVO;
import com.roydon.community.domain.vo.OrderListRes;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserOrderActivity extends BaseActivity {

    private RefreshLayout refreshLayout;
    private RecyclerView rvMallAddress;
    private LinearLayoutManager linearLayoutManager;
    private int pageNum = 1;
    private UserOrderAdapter userOrderAdapter;
    private List<MallOrderVO> userOrderList = new ArrayList<>();
    private ImageView ivReturn;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                userOrderAdapter.setDatas(userOrderList);
                userOrderAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_user_order;
    }

    @Override
    protected void initView() {
        // 刷新组件
        refreshLayout = findViewById(R.id.refreshLayout);
        // RecyclerView
        rvMallAddress = findViewById(R.id.rv_user_order);
        // 返回按钮
        ivReturn = findViewById(R.id.iv_return);
    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
        linearLayoutManager = new LinearLayoutManager(UserOrderActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMallAddress.setLayoutManager(linearLayoutManager);
        userOrderAdapter = new UserOrderAdapter(this);
        rvMallAddress.setAdapter(userOrderAdapter);

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
                        mHandler.sendEmptyMessage(0);
                    } else {
                        if (isRefresh) {
                            showSyncShortToast("暂无数据");
                        } else {
                            showSyncShortToast("没有更多数据");
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