package com.roydon.community;

import android.view.View;
import android.widget.Button;

import com.roydon.community.activity.HomeActivity;
import com.roydon.community.activity.LoginActivity;
import com.roydon.community.activity.RegisterActivity;
import com.roydon.community.utils.StringUtil;

/**
 * @author roydon
 */
public class MainActivity extends BaseActivity {

    private Button buttonLogin, buttonRegister;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        buttonLogin = findViewById(R.id.button_login);
        buttonRegister = findViewById(R.id.button_register);
    }

    @Override
    protected void initData() {
        if (!StringUtil.isEmpty(findByKey("token"))) {
            navigateTo(HomeActivity.class);
            finish();
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(LoginActivity.class);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(RegisterActivity.class);
            }
        });
    }
}