package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.EpidemicHealthCode;
import com.roydon.community.domain.response.EpidemicHealthCodeRes;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class HealthCodeActivity extends BaseActivity {
    private String TOOL_TITLE = "防疫健康码";

    private String BASE64_PREFIX = "data:image/png;base64,";

    // toolbar
    private ImageView ivReturn;
    private TextView tvToolTitle;

    /**
     * 进度条
     */
    private ProgressBar loadingSpinner;

    // 控件
    private ImageView ivHealthCode;
    private TextView tvUserName, tvUpdateTime;

    // 数据
    private EpidemicHealthCode epidemicHealthCode;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    showEpidemicHealthCode(epidemicHealthCode);
                    // 禁用进度条
                    loadingSpinner.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_health_code;
    }

    @Override
    protected void initView() {
        // toolbar
        ivReturn = findViewById(R.id.iv_return);
        tvToolTitle = findViewById(R.id.tv_tool_title);
        tvToolTitle.setText(TOOL_TITLE);
        // 加载进度条
        loadingSpinner = findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(View.VISIBLE);

        ivHealthCode = findViewById(R.id.iv_health_code);
        tvUserName = findViewById(R.id.tv_user_name);
        tvUpdateTime = findViewById(R.id.tv_update_time);

    }

    @Override
    protected void initData() {
        getUserHealthCode();
        ivReturn.setOnClickListener(v -> {
            finish();
        });

    }

    @SuppressLint("SimpleDateFormat")
    private void showEpidemicHealthCode(EpidemicHealthCode epidemicHealthCode) {
        String replace = epidemicHealthCode.getCodeBase64().replace(BASE64_PREFIX, "");
        byte[] decodedString = Base64.decode(replace, Base64.DEFAULT);
        ivHealthCode.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        tvUserName.setText(epidemicHealthCode.getUserName());
        tvUpdateTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(epidemicHealthCode.getUpdateTime()));
    }

    private void getUserHealthCode() {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.HEALTH_CODE_GET, params).getRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                EpidemicHealthCodeRes response = gson.fromJson(res, EpidemicHealthCodeRes.class);
                if (response != null && response.getCode() == 200) {
                    EpidemicHealthCode healthCode = response.getData();
                    Log.e("getUserHealthCode", healthCode.getUserName());
                    epidemicHealthCode = healthCode;
                    mHandler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}