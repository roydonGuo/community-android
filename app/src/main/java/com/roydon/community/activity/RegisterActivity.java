package com.roydon.community.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.utils.StringUtil;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegister;
    private TextView btnToLogin;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        btnToLogin = findViewById(R.id.tv_to_login);
    }

    @Override
    protected void initData() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etUsername.getText().toString().trim();
                String pwd = etPassword.getText().toString().trim();
                register(account, pwd);
            }
        });
        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToWithFlag(LoginActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });
    }

    private void register(String account, String pwd) {
        if (StringUtil.isEmpty(account)) {
            showShortToast("请输入账号");
            return;
        }
        if (StringUtil.isEmpty(pwd)) {
            showShortToast("请输入密码");
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("username", account);
        params.put("password", pwd);
        Api.build(ApiConfig.REGISTER, params).postRequest(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(() -> {
                    showLongToast(res);
                });
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("onFailure", e.toString());
            }
        });
    }
}