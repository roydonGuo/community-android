package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.EpidemicInoculationHistory;
import com.roydon.community.domain.response.EpidemicInoculationHistoryRes;

import java.util.HashMap;

public class InoculationHistoryActivity extends BaseActivity {
    private String TOOLBAR_TITLE = "疫苗接种记录";

    // handler
    private static final int HANDLER_WHAT_HISTORY = 1;

    // toolbar
    private ImageView ivReturn;
    private TextView tvToolTitle;

    // 控件
    private TextView tvRealName, tvTelephone, tvIdCard;
    private Button btnInoculationHistoryReport;

    private EpidemicInoculationHistory inoculationHistory;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_WHAT_HISTORY:
                    showInoculationHistory(inoculationHistory);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_inoculation_history;
    }

    @Override
    protected void initView() {
        // toolbar
        ivReturn = findViewById(R.id.iv_return);
        tvToolTitle = findViewById(R.id.tv_tool_title);
        tvToolTitle.setText(TOOLBAR_TITLE);
        // 控件
        tvRealName = findViewById(R.id.tv_real_name);
        tvTelephone = findViewById(R.id.tv_telephone);
        tvIdCard = findViewById(R.id.tv_id_card);
        btnInoculationHistoryReport = findViewById(R.id.btn_inoculation_history_report);

    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
        getInoculationHistory();
        btnInoculationHistoryReport.setOnClickListener(v -> {
            navigateTo(InoculationHistoryReportActivity.class);
        });

    }

    /**
     * 展示接种记录
     */
    private void showInoculationHistory(EpidemicInoculationHistory inoculationHistory) {
        tvRealName.setText(inoculationHistory.getRealName());
        tvTelephone.setText(inoculationHistory.getTelephone());
        tvIdCard.setText(inoculationHistory.getIdCard());
        // 禁用上报按钮
        btnInoculationHistoryReport.setEnabled(false);
    }

    /**
     * 获取接种记录
     */
    private void getInoculationHistory() {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.INOCULATION_HISTORY_USER, params).getRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("getInoculationHistory", res);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                EpidemicInoculationHistoryRes response = gson.fromJson(res, EpidemicInoculationHistoryRes.class);
                if (response != null && response.getCode() == 200 && response.getData() != null) {
                    inoculationHistory = response.getData();
                    mHandler.sendEmptyMessage(HANDLER_WHAT_HISTORY);
                }
            }

            @Override
            public void onFailure(Exception e) {
                showShortToast("获取失败");
            }
        });
    }

}