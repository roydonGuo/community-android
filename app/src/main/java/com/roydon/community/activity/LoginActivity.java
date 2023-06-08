package com.roydon.community.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.entity.LoginBody;
import com.roydon.community.entity.LoginResponse;

import java.util.HashMap;

/**
 * @author roydon
 */
public class LoginActivity extends BaseActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showShortToast("登录中...");
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                login(username, password);
                //                navigateToWithFlag(HomeActivity.class,
                //                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            }
        });
    }

    private void login(String username, String password) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        LoginBody loginBody = new LoginBody(etUsername.toString(), etPassword.toString());
        Api.config(ApiConfig.LOGIN, params).postRequest(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("onSuccess", res);
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                if (loginResponse.getCode() == 200) {
                    String token = loginResponse.getToken();
                    insertVal("token", token);
//                    navigateToWithFlag(HomeActivity.class,
//                            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    runOnUiThread(() -> {
                        showLongToast("登录成功" + token);
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