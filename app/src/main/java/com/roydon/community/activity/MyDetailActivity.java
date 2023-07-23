package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.roydon.community.BaseActivity;
import com.roydon.community.R;

public class MyDetailActivity extends BaseActivity {

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
    }

    @Override
    protected void initData() {
    }

}
