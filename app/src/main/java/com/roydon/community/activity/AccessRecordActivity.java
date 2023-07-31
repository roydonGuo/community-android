package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.response.BaseResponse;
import com.roydon.community.enums.AccessTypeEnum;
import com.roydon.community.enums.ReportTypeEnum;
import com.roydon.community.utils.string.StringUtil;
import com.roydon.community.utils.string.TelephoneUtils;

import java.util.HashMap;

/**
 * 进出社区记录activity
 */
public class AccessRecordActivity extends BaseActivity {

    /**
     * 顶部top-bar功能栏
     */
    private ImageView ivReturn;

    // 表单区域
    private EditText etRealName, etTelephone, etPlaceEnd, etRemark;
    private TextView tvPlaceStart;
    private RadioGroup rgAccessType;
    private RadioButton rbOut;
    private String accessType = "0";

    String realAddress;
    String regionCode;

    // 新增按钮
    private Button addRecord;

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
        return R.layout.activity_access_record;
    }

    @Override
    protected void initView() {
        // 返回按钮
        ivReturn = findViewById(R.id.iv_return);
        // 输入区域
        // 真实姓名
        etRealName = findViewById(R.id.et_real_name);
        // 手机号码
        etTelephone = findViewById(R.id.et_telephone);
        // 进出类型，0出1进
        rgAccessType = findViewById(R.id.rg_access_type);
        rgAccessType.check(R.id.rb_out);
        // 出发地
        tvPlaceStart = findViewById(R.id.tv_place_start);
        //目的地
        etPlaceEnd = findViewById(R.id.et_place_end);
        //备注
        etRemark = findViewById(R.id.et_remark);
        rgAccessType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbAccessType = findViewById(radioGroup.getCheckedRadioButtonId());
                if (rbAccessType.getText().toString().equals(AccessTypeEnum.OUT.getInfo())) {
                    // 出被选择
                    accessType = AccessTypeEnum.OUT.getCode();
                } else {
                    accessType = AccessTypeEnum.IN.getCode();
                }
            }
        });
        tvPlaceStart.setOnClickListener(v -> {
            Intent intent = new Intent(this, BDAddressSelectActivity.class);
            //要求目标页面传真实地址数据回来
            startActivityForResult(intent, 100);
        });
        // 新增收货地址按钮
        addRecord = findViewById(R.id.btn_save_record);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            if (data != null) {
                realAddress = data.getExtras().getString("realAddress");
                regionCode = data.getExtras().getString("regionCode");
                tvPlaceStart.setText(realAddress);
            }
        }
    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
        addRecord.setOnClickListener(v -> {
            if (etRealName.getText().toString().length() >= 10 || StringUtil.isEmpty(etRealName.getText().toString())) {
                showShortToast("请输入正确姓名");
                return;
            }
            if (!TelephoneUtils.isValidPhoneNumber(etTelephone.getText().toString()) || StringUtil.isEmpty(etTelephone.getText().toString())) {
                showShortToast("请输入正确的手机号码");
                return;
            }
            if (StringUtil.isEmpty(tvPlaceStart.getText().toString())) {
                showShortToast("请选择出发地");
                return;
            }
            if (StringUtil.isEmpty(etPlaceEnd.getText().toString())) {
                showShortToast("请填写目的地");
                return;
            }
            saveAccessRecord(etRealName.getText().toString(), etTelephone.getText().toString(), accessType, ReportTypeEnum.ONESELF.getCode(), tvPlaceStart.getText().toString(), etPlaceEnd.getText().toString(), etRemark.getText().toString());
        });
    }

    private void saveAccessRecord(String realName, String telephone, String accessType, String reportType, String placeStart, String placeEnd, String remark) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("realName", realName);
        params.put("telephone", telephone);
        params.put("accessType", accessType);
        params.put("reportType", reportType);
        params.put("placeStart", placeStart);
        params.put("placeEnd", placeEnd);
        params.put("remark", remark);
        Api.build(ApiConfig.EPIDEMIC_ACCESS_ADD, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("saveAccessRecord", res);
                BaseResponse response = new Gson().fromJson(res, BaseResponse.class);
                if (response != null && response.getCode() == 200) {
                    finish();
                    showSyncShortToast("报备成功");
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

}