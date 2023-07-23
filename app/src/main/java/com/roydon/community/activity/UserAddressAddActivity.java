package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.MallUserAddress;
import com.roydon.community.domain.vo.UserAddressListRes;

import java.util.HashMap;
import java.util.List;

public class UserAddressAddActivity extends BaseActivity {

    private ImageView ivReturn;
    private TextView tvCity;
    private Button addAddress;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_user_address_add;
    }

    @Override
    protected void initView() {
        // 返回按钮
        ivReturn = findViewById(R.id.iv_return);
        tvCity = findViewById(R.id.tv_city);
        tvCity.setOnClickListener(v -> {
//            navigateTo(BDAddressSelectActivity.class);
            Intent intent = new Intent(this, BDAddressSelectActivity.class);
            //要求目标页面传真实地址数据回来
            startActivityForResult(intent, 100);
        });
        // 新增收货地址按钮
        addAddress = findViewById(R.id.btn_save_address);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            if (data != null) {
                String realAddress = data.getExtras().getString("realAddress");
                tvCity.setText(realAddress);
            }
        }
    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
        addAddress.setOnClickListener(v -> {

        });

    }

    private void saveAddress(MallUserAddress mallUserAddress) {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.MALL_ADDRESS_LIST, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("saveAddress", res);
                // 后端传递时间格式解析
//                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                UserAddressListRes response = new Gson().fromJson(res, UserAddressListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<MallUserAddress> userAddressList = response.getData();
                    if (userAddressList != null && userAddressList.size() > 0) {
                        mHandler.sendEmptyMessage(0);
                    } else {
                        showSyncShortToast("新增失败");

                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

}