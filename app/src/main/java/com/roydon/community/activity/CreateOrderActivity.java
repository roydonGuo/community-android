package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.adapter.OrderGoodAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.MallOrder;
import com.roydon.community.domain.entity.MallUserAddress;
import com.roydon.community.domain.entity.MallUserCartVO;
import com.roydon.community.domain.vo.MallUserCartListRes;
import com.roydon.community.domain.vo.OrderCreateRes;
import com.roydon.community.domain.vo.UserAddressRes;
import com.roydon.community.utils.DoubleUtils;
import com.roydon.community.utils.string.StringUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CreateOrderActivity extends BaseActivity {
    private String TOOL_TITLE = "创建订单";

    private RefreshLayout refreshLayout;
    private TextView tvAddressTelephone, tvAddressComplete;
    private RecyclerView rvGoods;
    private LinearLayoutManager linearLayoutManager;
    private int pageNum = 1;
    private OrderGoodAdapter orderGoodAdapter;

    private MallUserAddress userAddress;
    private List<MallUserCartVO> cartList = new ArrayList<>();

    private Button pay;
    private TextView totalPrice, less, add;
    private ImageView ivGoodsImage, ivReturn;

    @Override
    protected int initLayout() {
        return R.layout.activity_create_order;
    }

    @Override
    protected void initView() {
        initToolBar(TOOL_TITLE);
        tvAddressTelephone = findViewById(R.id.tv_address_telephone);
        tvAddressComplete = findViewById(R.id.tv_address_complete);
        refreshLayout = findViewById(R.id.refreshLayout);
        rvGoods = findViewById(R.id.rv_goods);
        totalPrice = findViewById(R.id.tv_total_price);
        pay = findViewById(R.id.btn_pay);
    }

    @Override
    protected void initData() {
        linearLayoutManager = new LinearLayoutManager(CreateOrderActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGoods.setLayoutManager(linearLayoutManager);
        orderGoodAdapter = new OrderGoodAdapter(this);
        rvGoods.setAdapter(orderGoodAdapter);
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            goodsIds = bundle.getStringArrayList("goodsIds");
//            this.getCartGoodsList();
//        }
        getDefaultAddress();
        getCartGoodsList();
        pay.setOnClickListener(v -> {
            List<String> goodsIds = cartList.stream().map(MallUserCartVO::getGoodsId).collect(Collectors.toList());
            createOrder(userAddress.getAddressId(), goodsIds);
        });
    }

    private void getDefaultAddress() {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.MALL_DEFAULT_ADDRESS, params).getRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("getDefaultAddress", res);
                // 后端传递时间格式解析
//                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                UserAddressRes response = new Gson().fromJson(res, UserAddressRes.class);
                if (response != null && response.getCode() == 200) {
                    userAddress = response.getData();
                    if (userAddress != null) {
                        // 使用handler将数据传递给主线程
                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = userAddress;
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint({"NotifyDataSetChanged", "SetTextI18n", "HandlerLeak"})
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
//                    Double collect = cartList.stream().mapToDouble(MallUserCartVO::getGoodsPrice).sum();
                    List<Double> doubles = cartList.stream().map(MallUserCartVO::getGoodsPrice).collect(Collectors.toList());
                    double v = DoubleUtils.addDoubleList(doubles);
                    totalPrice.setText("￥" + v);
                    orderGoodAdapter.setDatas(cartList);
                    orderGoodAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    MallUserAddress address = (MallUserAddress) msg.obj;
                    Log.e("onSuccess", address.getAddressId());
                    showAddressView(address);
                    break;
                case 3:
                    finish();
                    break;
            }
        }
    };

    @SuppressLint("SetTextI18n")
    private void showAddressView(MallUserAddress address) {
        tvAddressTelephone.setText(address.getNickname() + "，" + address.getTelephone());
        tvAddressComplete.setText(address.getCompleteAddress());
    }

    private void getCartGoodsList() {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.MALL_ALL_CART, params).getRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                // 后端传递时间格式解析
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                MallUserCartListRes response = gson.fromJson(res, MallUserCartListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<MallUserCartVO> cartVOList = response.getData();
                    if (cartVOList != null && cartVOList.size() > 0) {
                        cartList = cartVOList;
                        mHandler.sendEmptyMessage(2);
                    } else {
                        Log.e("getCartGoodsList", "没有更多数据");
                        showSyncShortToast("没有更多数据");
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    // 创建订单
    private void createOrder(String addressId, List<String> goodsIds) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        params.put("goodsIds", goodsIds);
        Api.build(ApiConfig.MALL_ORDER_CREATE, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                // 后端传递时间格式解析
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                OrderCreateRes response = gson.fromJson(res, OrderCreateRes.class);
                if (response != null && response.getCode() == 200) {
                    MallOrder mallOrder = response.getData();
                    if (StringUtil.isNotNull(mallOrder)) {
                        showSyncShortToast("创建订单成功");
                        mHandler.sendEmptyMessage(3);
                    } else {
                        showSyncShortToast("没有更多数据");
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

}