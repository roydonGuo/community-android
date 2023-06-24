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
import com.roydon.community.domain.vo.GoodsDetailRes;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class GoodDetailActivity extends BaseActivity {

    private TextView tvGoodTitle, tvGoodPrice, tvGoodDetail;
    private ImageView ivGoodsImage, ivReturn;
    private String goodsId;
    private Button btnGoodAddCart;

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

    @SuppressLint("SetJavaScriptEnabled")
    private void goodsDetailShow(MallGoodsVO goodsVO) {
        Picasso.with(this)
                .load(goodsVO.getGoodsImg())
                .into(ivGoodsImage);
        tvGoodTitle.setText(goodsVO.getGoodsTitle());
        tvGoodPrice.setText("￥" + goodsVO.getGoodsPrice());
        tvGoodDetail.setText(goodsVO.getGoodsDetails());
    }

}