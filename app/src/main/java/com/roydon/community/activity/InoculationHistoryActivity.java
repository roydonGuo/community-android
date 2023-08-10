package com.roydon.community.activity;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.roydon.community.BaseActivity;
import com.roydon.community.R;

public class InoculationHistoryActivity extends BaseActivity {
    private String TOOLBAR_TITLE = "疫苗接种记录";

    // toolbar
    private ImageView ivReturn;
    private TextView tvToolTitle;

    // 控件
    private Button btnInoculationHistoryReport;

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
        btnInoculationHistoryReport = findViewById(R.id.btn_inoculation_history_report);

    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
        btnInoculationHistoryReport.setOnClickListener(v -> {
            navigateTo(InoculationHistoryReportActivity.class);
        });

    }


    private void getInoculationHistory() {


    }

}