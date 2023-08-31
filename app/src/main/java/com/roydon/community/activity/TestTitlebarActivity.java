package com.roydon.community.activity;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.ui.popup.MenuPopup;

public class TestTitlebarActivity extends BaseActivity  {

    private TitleBar mTitleBar;

    @Override
    protected int initLayout() {
        return R.layout.activity_test_titlebar;
    }

    @Override
    protected void initView() {
        mTitleBar = findViewById(R.id.title_bar);
    }

    @Override
    protected void initData() {
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }

            @Override
            public void onTitleClick(TitleBar titleBar) {
                OnTitleBarListener.super.onTitleClick(titleBar);
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                OnTitleBarListener.super.onRightClick(titleBar);
                // 菜单弹窗
                new MenuPopup.Builder(TestTitlebarActivity.this)
                        .setList("选择拍照", "选取相册")
                        .setListener((MenuPopup.OnListener<String>) (popupWindow, position, s) -> toast(s))
                        .showAsDropDown(titleBar.getRightView());

            }
        });
    }
}