package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.roydon.community.BaseActivity;
import com.roydon.community.R;

public class MyDetailActivity extends BaseActivity {

    /**
     * 顶部top-bar功能栏
     */
    private ImageView ivReturn;

    @Override
    protected int initLayout() {
        return R.layout.activity_my_detail;
    }

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
