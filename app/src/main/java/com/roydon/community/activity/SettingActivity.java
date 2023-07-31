package com.roydon.community.activity;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.roydon.community.BaseActivity;
import com.roydon.community.R;

public class SettingActivity extends BaseActivity {

    private ImageView ivReturn;
    private Button btnRegister;
    private TextView btnToLogin;

    @Override
    protected int initLayout() {
        return R.layout.activity_setting;
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