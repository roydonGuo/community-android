package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.action.StatusAction;
import com.roydon.community.aop.CheckNet;
import com.roydon.community.constants.IntentKey;
import com.roydon.community.utils.string.StringUtil;
import com.roydon.community.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

@SuppressLint("SetJavaScriptEnabled")
public class WebviewActivity extends BaseActivity implements StatusAction {
    private String TOOL_TITLE = "浏览器";

    private WebView mWebView;
//    private String url = "http://106.14.105.101:88";

    private HintLayout hintLayout;
    private SmartRefreshLayout refreshLayout;

    @CheckNet
    public static void start(Context context, String url) {
        if (url == null || "".equals(url)) {
            return;
        }
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra(IntentKey.URL, url);
        context.startActivity(intent);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView() {
        initToolBar(TOOL_TITLE);
        mWebView = findViewById(R.id.webview);
        initWebView();
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (StringUtil.isNotEmpty(title)) {
                    initToolBar(title);
                }
            }
        });
        hintLayout = findViewById(R.id.hl_webview_hint);
        refreshLayout = findViewById(R.id.refreshLayout);

    }

    @Override
    protected void initData() {
        String url = getString(IntentKey.URL);
        mWebView.loadUrl(url);
        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            mWebView.reload();
        });
    }

    @Override
    public HintLayout getHintLayout() {
        return hintLayout;
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
