package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.roydon.community.BaseActivity;
import com.roydon.community.R;

/**
 * 进出社区记录activity
 */
public class AccessRecordActivity extends BaseActivity {

    /**
     * 顶部top-bar功能栏
     */
    private ImageView ivReturn;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
//                    Double collect = cartList.stream().mapToDouble(MallUserCartVO::getGoodsPrice).sum();
//                    List<Double> doubles = cartList.stream().map(MallUserCartVO::getGoodsPrice).collect(Collectors.toList());
//                    double v = DoubleUtils.addDoubleList(doubles);
//                    totalPrice.setText(v + "");
//                    cartAdapter.setDatas(cartList);
//                    cartAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_access_record;
    }

    @Override
    protected void initView() {
        ivReturn = findViewById(R.id.iv_return);
    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
    }


}