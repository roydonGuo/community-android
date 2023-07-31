package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.MallGoodsVO;
import com.roydon.community.domain.entity.MallOrder;
import com.roydon.community.domain.response.BaseResponse;
import com.roydon.community.domain.vo.GoodsDetailRes;
import com.roydon.community.domain.vo.OrderCreateRes;
import com.roydon.community.utils.string.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GoodDetailActivity extends BaseActivity {

    private TextView tvGoodTitle, tvGoodPrice, tvGoodDetail;
    private ImageView ivGoodsImage, ivReturn, ivMallCart;
    private String goodsId;
    private Button btnGoodAddCart, btnCreateOrder;

    @Override
    protected int initLayout() {
        return R.layout.activity_good_detail;
    }

    @Override
    protected void initView() {
        ivGoodsImage = findViewById(R.id.iv_goods_img);
        tvGoodTitle = findViewById(R.id.tv_good_title);
        tvGoodPrice = findViewById(R.id.tv_good_price);
        tvGoodDetail = findViewById(R.id.tv_good_detail);
        ivReturn = findViewById(R.id.iv_return);
        btnGoodAddCart = findViewById(R.id.btn_good_add_card);
        // 跳转到购物车
        ivMallCart = findViewById(R.id.iv_mall_cart);
        btnCreateOrder = findViewById(R.id.btn_create_order);

    }

    @Override
    protected void initData() {
//        LoadingDialog.getInstance(this).show();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            goodsId = bundle.getString("goodsId");
            this.getGoodDetail(goodsId);
        }
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnGoodAddCart.setOnClickListener((v) -> {
            addCart(goodsId);
        });
        ivMallCart.setOnClickListener(v -> {
            navigateTo(CartActivity.class);
        });
        btnCreateOrder.setOnClickListener(v -> {
            List<String> goodsIds = new ArrayList<>();
            goodsIds.add(goodsId);
            showShortToast(goodsIds.toString());
            // TODO 立即下单，先到创建订单页面
//            createOrder(userAddress.getAddressId(), goodsIds);
        });
    }

    private void getGoodDetail(String goodsId) {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.MALL_GOODS_DETAIL + "/" + goodsId, params).getRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                // 后端传递时间格式解析
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                GoodsDetailRes response = gson.fromJson(res, GoodsDetailRes.class);
                if (response != null && response.getCode() == 200) {
                    MallGoodsVO goodsVO = response.getData();
                    // 使用handler将数据传递给主线程
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = goodsVO;
                    mHandler.sendMessage(message);
                } else {
                    finish();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private synchronized void addCart(String goodsId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("goodsId", goodsId);
        Api.build(ApiConfig.MALL_ADD_CART, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                // 后端传递时间格式解析
                BaseResponse response = new Gson().fromJson(res, BaseResponse.class);
                if (response != null && response.getCode() == 200) {
                    showSyncShortToast("添加成功");
                } else {
                    finish();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                MallGoodsVO goodsVO = (MallGoodsVO) msg.obj;
                Log.e("onSuccess", goodsVO.getGoodsId());
                goodsDetailShow(goodsVO);
//                LoadingDialog.getInstance(getApplicationContext()).dismiss();
            }
        }
    };

    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
    private void goodsDetailShow(MallGoodsVO goodsVO) {
        Picasso.with(this)
                .load(goodsVO.getGoodsImg())
                .into(ivGoodsImage);
        tvGoodTitle.setText(goodsVO.getGoodsTitle());
        tvGoodPrice.setText("￥" + goodsVO.getGoodsPrice());
        tvGoodDetail.setText(goodsVO.getGoodsDetails());
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