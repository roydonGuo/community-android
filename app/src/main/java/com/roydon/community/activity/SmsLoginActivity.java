package com.roydon.community.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.roydon.community.domain.vo.PhoneCodeRes;
import com.roydon.community.domain.vo.SmsLoginRes;
import com.roydon.community.utils.string.TelephoneUtils;

import java.util.HashMap;

public class SmsLoginActivity extends BaseActivity {

    private EditText etTelephone;
    private EditText etPhoneCode;
    private Button btnGetPhoneCode, btnSmsLogin;
    private TextView tvToLogin, tvToRegister;

    private int countSeconds = 60; // 倒计时秒数

    @Override
    protected int initLayout() {
        return R.layout.activity_sms_login;
    }

    private Handler mCountHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (countSeconds > 0) {
                --countSeconds;
                btnGetPhoneCode.setText(countSeconds + "秒后重试");
                mCountHandler.sendEmptyMessageDelayed(0, 1000);
            } else {
                countSeconds = 60;
                btnGetPhoneCode.setText("重新获取");
            }
        }
    };

    @Override
    protected void initView() {
        etTelephone = findViewById(R.id.et_telephone);
        etPhoneCode = findViewById(R.id.et_phoneCode);
        // 获取验证码按钮
        btnGetPhoneCode = findViewById(R.id.btn_getPhoneCode);
        // 登录按钮
        btnSmsLogin = findViewById(R.id.btn_sms_login);
        // 账号密码登录tv
        tvToLogin = findViewById(R.id.tv_to_login);
        // 去注册tv
        tvToRegister = findViewById(R.id.tv_to_register);
    }

    @Override
    protected void initData() {
        // 获取短信验证码
        btnGetPhoneCode.setOnClickListener(v -> {
            if (countSeconds == 60) {
                String telephone = etTelephone.getText().toString();
                if (!TelephoneUtils.isValidPhoneNumber(telephone)) {
                    showShortToast("手机号码错误");
                    return;
                }
                getPhoneCode(telephone);
            } else {
                showLongToast("不能重复发送验证码");
            }
        });
        btnSmsLogin.setOnClickListener(v -> {
            String telephone = etTelephone.getText().toString().trim();
            String phoneCode = etPhoneCode.getText().toString().trim();
            smsLogin(telephone, phoneCode);
        });
        tvToLogin.setOnClickListener(v -> {
            navigateToWithFlag(LoginActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        });
        tvToRegister.setOnClickListener(v -> {
            navigateToWithFlag(RegisterActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        });
    }

    // 获取验证码信息
    private void getPhoneCode(String telephone) {
        Api.buildNoParams(ApiConfig.SMS_SEND_CODE + telephone).getRequest(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("onSuccess", res);
                PhoneCodeRes codeRes = new Gson().fromJson(res, PhoneCodeRes.class);
                if (codeRes.getCode() == 200) {
                    startCountBack();
                } else {
                    showSyncShortToast("接口超时");
                }
            }

            @Override
            public void onFailure(Exception e) {
                showSyncShortToast("接口超时");
            }
        });
    }

    //获取验证码信息,进行计时操作
    private void startCountBack() {
        runOnUiThread(() -> {
            btnGetPhoneCode.setText(countSeconds + "");
            mCountHandler.sendEmptyMessage(0);
        });
    }

    private void smsLogin(String telephone, String phoneCode) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("telephone", telephone);
        params.put("phoneCode", phoneCode);
        Api.build(ApiConfig.SMS_LOGIN, params).postRequest(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("onSuccess", res);
                SmsLoginRes smsLoginResponse = new Gson().fromJson(res, SmsLoginRes.class);
                if (smsLoginResponse.getCode() == 200) {
                    String token = smsLoginResponse.getToken();
                    insertVal(Constants.TOKEN, token);
                    navigateToWithFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    showSyncLongToast("登录成功");
                } else {
                    showSyncShortToast(smsLoginResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Exception e) {
                showSyncLongToast("接口失效");
            }
        });
    }


}