package com.roydon.community.activity;

import com.roydon.community.BaseActivity;
import com.roydon.community.R;

public class AboutUsActivity extends BaseActivity {
    private String TOOL_TITLE = "关于我们";

    @Override
    protected int initLayout() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        initToolBar(TOOL_TITLE);
    }

    @Override
    protected void initData() {

    }
}