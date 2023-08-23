package com.roydon.community.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.constants.BundleConstants;
import com.roydon.community.domain.response.BaseResponse;
import com.roydon.community.domain.vo.AppUser;
import com.roydon.community.utils.string.StringUtil;

import java.util.HashMap;

public class NatOrderActivity extends BaseActivity {
    private String TOOL_TITLE = "核酸预约";

    private EditText etRealName, etTelephone, etIdCard;

    private Button btnNatOrder;

    private AppUser appUser;

    @Override
    protected int initLayout() {
        return R.layout.activity_nat_order;
    }

    @Override
    protected void initView() {
        etRealName = findViewById(R.id.et_real_name);
        etTelephone = findViewById(R.id.et_telephone);
        etIdCard = findViewById(R.id.et_id_card);
        btnNatOrder = findViewById(R.id.btn_nat_order);

    }

    @Override
    protected void initData() {
        initToolBar(TOOL_TITLE);
        // 获取页面传来 appUser
        Bundle extras = getIntent().getExtras();
        if (StringUtil.isNotNull(extras)) {
            appUser = (AppUser) extras.getSerializable(BundleConstants.APPUSER);
            showAppUserInForm(appUser);
        }
        btnNatOrder.setOnClickListener(v -> {
            natOrderQuick();
        });

    }

    private void showAppUserInForm(AppUser appUser) {
        etRealName.setText(appUser.getRealName());
        etTelephone.setText(appUser.getPhonenumber());
        etIdCard.setText(appUser.getIdCard());
    }

    /**
     * 用户信息
     */
    private void natOrderQuick() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("realName", etRealName.getText().toString().trim());
        params.put("telephone", etTelephone.getText().toString().trim());
        params.put("idCard", etIdCard.getText().toString().trim());
        Api.build(ApiConfig.NAT_ORDER_QUICK, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("natOrderQuick", res);
                BaseResponse response = new Gson().fromJson(res, BaseResponse.class);
                if (response != null && response.getCode() == 200) {
                    finish();
                    showSyncShortToast("预约成功");
                } else {
                    showSyncShortToast("预约失败" + response.getMsg());
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}