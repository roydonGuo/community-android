package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

@SuppressLint("SetJavaScriptEnabled")
public class WebviewActivity extends BaseActivity {

    private String TOOL_TITLE = "浏览器";

    // toolbar
    private ImageView ivReturn;
    private TextView tvToolTitle;

    private WebView mWebView;
    private String url = "http://106.14.105.101:88";

    private SmartRefreshLayout refreshLayout;

    @Override
    protected int initLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView() {
        mWebView = findViewById(R.id.webview);
        ivReturn = findViewById(R.id.iv_return);
        tvToolTitle = findViewById(R.id.tv_tool_title);
        tvToolTitle.setText(TOOL_TITLE);
        initWebView();
        refreshLayout = findViewById(R.id.refreshLayout);

        // 不显示滚动条
//        setVerticalScrollBarEnabled(false);
//        setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
        mWebView.loadUrl(url);
        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            mWebView.reload();
        });
    }

    @Override
    protected void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        // 允许文件访问
        settings.setAllowFileAccess(true);
        // 允许网页定位
        settings.setGeolocationEnabled(true);
        // 允许保存密码
        //settings.setSavePassword(true);
        // 开启 JavaScript
        settings.setJavaScriptEnabled(true);
        // 允许网页弹对话框
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 加快网页加载完成的速度，等页面完成再加载图片
        settings.setLoadsImagesAutomatically(true);
        // 本地 DOM 存储（解决加载某些网页出现白板现象）
        settings.setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 解决 Android 5.0 上 WebView 默认不允许加载 Http 与 Https 混合内容
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

}
