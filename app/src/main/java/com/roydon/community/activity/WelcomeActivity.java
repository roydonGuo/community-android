package com.roydon.community.activity;

import android.content.Intent;
import android.os.Handler;

import com.roydon.community.BaseActivity;
import com.roydon.community.MainActivity;
import com.roydon.community.R;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected int initLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    @Override
    protected void initData() {

    }


}