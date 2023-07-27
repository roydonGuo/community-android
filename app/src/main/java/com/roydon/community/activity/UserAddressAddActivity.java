package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.response.AddressAddRes;
import com.roydon.community.utils.StringUtil;
import com.roydon.community.utils.TelephoneUtils;

import java.util.HashMap;

public class UserAddressAddActivity extends BaseActivity {

    private ImageView ivReturn;
    private TextView tvCity;
    private Button addAddress;
    private EditText etNickname, etTelephone, etCompleteAddress;
    private Switch addressIsDefault;
    private String isDefault = "0";

    /**
     * 新增地址需要的数据
     * <p>
     * {
     * "nickName": "guoyicheng",
     * "telephone": "18203707837",
     * "regionCode": "411481",
     * "completeAddress": "测试默认地址~ o(*￣▽￣*)o",
     * "isDefault": "1"
     * }
     */

    String realAddress;
    String regionCode;

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
    protected int initLayout() {
        return R.layout.activity_user_address_add;
    }

    @Override
    protected void initView() {
        // 返回按钮
        ivReturn = findViewById(R.id.iv_return);
        // 输入区域
        etNickname = findViewById(R.id.et_nickname);
        etTelephone = findViewById(R.id.et_telephone);
        tvCity = findViewById(R.id.tv_city);
        etCompleteAddress = findViewById(R.id.et_complete_address);
        addressIsDefault = findViewById(R.id.address_is_default);

        // 设为默认地址
        addressIsDefault.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isDefault = "1";
            } else {
                isDefault = "0";
            }
        });
        tvCity.setOnClickListener(v -> {
//            navigateTo(BDAddressSelectActivity.class);
            Intent intent = new Intent(this, BDAddressSelectActivity.class);
            //要求目标页面传真实地址数据回来
            startActivityForResult(intent, 100);
        });
        // 新增收货地址按钮
        addAddress = findViewById(R.id.btn_save_address);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            if (data != null) {
                realAddress = data.getExtras().getString("realAddress");
                regionCode = data.getExtras().getString("regionCode");
                tvCity.setText(realAddress);
            }
        }
    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
        addAddress.setOnClickListener(v -> {
            if (etNickname.getText().toString().length() >= 12 || StringUtil.isEmpty(etNickname.getText().toString())) {
                showShortToast("请输入合法昵称");
                return;
            }
            if (!TelephoneUtils.isValidPhoneNumber(etTelephone.getText().toString()) || StringUtil.isEmpty(etTelephone.getText().toString())) {
                showShortToast("请输入正确的手机号码");
                return;
            }
            if (StringUtil.isEmpty(tvCity.getText().toString())) {
                showShortToast("请选择地址");
                return;
            }
            saveAddress(etNickname.getText().toString(), etTelephone.getText().toString(), regionCode, realAddress + etCompleteAddress.getText().toString(), isDefault);

        });

    }

    private void saveAddress(String nickname, String telephone, String regionCode, String completeAddress, String isDefault) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("nickname", nickname);
        params.put("telephone", telephone);
        params.put("regionCode", regionCode);
        params.put("completeAddress", completeAddress);
        params.put("isDefault", isDefault);
        Api.build(ApiConfig.MALL_ADD_ADDRESS, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("saveAddress", res);
                // 后端传递时间格式解析
//                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                AddressAddRes response = new Gson().fromJson(res, AddressAddRes.class);
                if (response != null && response.getCode() == 200 && response.getData() != null) {
                    showShortToast("新增成功");
                    finish();
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

}