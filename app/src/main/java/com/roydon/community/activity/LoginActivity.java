package com.roydon.community.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.constants.Constants;
import com.roydon.community.domain.vo.LoginResponse;

import java.util.HashMap;

/**
 * @author roydon
 */
public class LoginActivity extends BaseActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvToRegister;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvToRegister = findViewById(R.id.tv_to_register);
    }

    @Override
    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                login(username, password);
            }
        });
        tvToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToWithFlag(RegisterActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });
    }

    private void login(String username, String password) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        Api.build(ApiConfig.LOGIN, params).postRequest(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("onSuccess", res);
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                if (loginResponse.getCode() == 200) {
                    String token = loginResponse.getToken();
                    insertVal(Constants.TOKEN, token);
                    navigateToWithFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    runOnUiThread(() -> {
                        showLongToast("登录成功");
                    });
                } else {
                    runOnUiThread(() -> {
                        showShortToast("登录失败");
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}