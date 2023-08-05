package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.roydon.community.BaseActivity;
import com.roydon.community.R;

@SuppressLint("SetJavaScriptEnabled")
public class WebviewActivity extends BaseActivity {

    private WebView mWebView;
    private String url;

    @Override
    protected int initLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView() {
        mWebView = findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    protected void initData() {
        mWebView.loadUrl("http://106.14.105.101:88");
    }

    private void initWebView() {
    }

}
