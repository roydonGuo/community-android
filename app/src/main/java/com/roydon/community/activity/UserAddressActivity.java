package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.adapter.AddressAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.MallUserAddress;
import com.roydon.community.domain.response.BaseResponse;
import com.roydon.community.domain.vo.UserAddressListRes;
import com.roydon.community.view.AlertDialogX;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserAddressActivity extends BaseActivity {

    private RefreshLayout refreshLayout;
    private RecyclerView rvMallAddress;
    private LinearLayoutManager linearLayoutManager;
    private int pageNum = 1;
    private AddressAdapter addressAdapter;
    private List<MallUserAddress> addressList = new ArrayList<>();
    private ImageView ivReturn;
    private Button addAddress;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    addressAdapter.setDatas(addressList);
                    addressAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_user_address;
    }

    @Override
    protected void initView() {
        // 刷新组件
        refreshLayout = findViewById(R.id.refreshLayout);
        // RecyclerView
        rvMallAddress = findViewById(R.id.rv_mall_address);
        // 返回按钮
        ivReturn = findViewById(R.id.iv_return);
        // 新增收货地址按钮
        addAddress = findViewById(R.id.btn_add_address);
    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
        linearLayoutManager = new LinearLayoutManager(UserAddressActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMallAddress.setLayoutManager(linearLayoutManager);
        addressAdapter = new AddressAdapter(this);
        rvMallAddress.setAdapter(addressAdapter);
        addressAdapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                MallUserAddress address = addressList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("addressId", address.getAddressId());
                showShortToast("addressId" + address.getAddressId());
//                navigateToWithBundle(GoodDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                String title = "删除收货地址";
                String msg = "确定删除此收货地址吗？";
                AlertDialogX.showCustomAlertDialog(UserAddressActivity.this, title, msg, new View.OnClickListener() {
                    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
                    @Override
                    public void onClick(View view) {
                        // TODO 删除收货地址
                        showShortToast("删除成功");
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });
        addAddress.setOnClickListener(v -> {
            navigateTo(UserAddressAddActivity.class);
        });
        refreshLayout.setOnRefreshListener(r -> {
            pageNum = 1;
            getAddressList(true);
        });
        refreshLayout.setOnLoadMoreListener(r -> {
            pageNum++;
            getAddressList(false);
        });
        getAddressList(true);
    }

    private void getAddressList(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE);
        Api.build(ApiConfig.MALL_ADDRESS_LIST, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("getAddressList", res);
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                // 后端传递时间格式解析
//                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                UserAddressListRes response = new Gson().fromJson(res, UserAddressListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<MallUserAddress> userAddressList = response.getData();
                    if (userAddressList != null && userAddressList.size() > 0) {
                        if (isRefresh) {
                            addressList = userAddressList;
                        } else {
                            addressList.addAll(userAddressList);
                        }
                        mHandler.sendEmptyMessage(0);
                    } else {
                        if (isRefresh) {
                            Log.e("getAddressList", "暂无数据");
                            showSyncShortToast("暂无数据");
                        } else {
                            Log.e("getAddressList", "没有更多数据");
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

    private void delCart(String cartId) {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.MALL_DEL_CART + cartId, params).delRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                BaseResponse response = new Gson().fromJson(res, BaseResponse.class);
                if (response != null && response.getCode() == 200) {
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }
}