package com.roydon.community.activity;

import android.view.Gravity;

import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.manager.CacheDataManager;
import com.roydon.community.ui.dialog.MenuDialog;
import com.roydon.library.action.AnimAction;
import com.roydon.library.layout.SettingBar;

public class SettingActivity extends BaseActivity {
    private String TOOL_TITLE = "设置";

    /**
     * 语言切换
     */
    private SettingBar sbSettingLanguage;

    /**
     * 缓存
     */
    private SettingBar sbSettingCache;

    @Override
    protected int initLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        initToolBar(TOOL_TITLE);
        sbSettingLanguage = findViewById(R.id.sb_setting_language);
        sbSettingLanguage.setOnClickListener(v -> {
            // 底部选择框
            new MenuDialog.Builder(this)
                    // 设置点击按钮后不关闭对话框
                    //.setAutoDismiss(false)
                    .setList(R.string.setting_language_simple, R.string.setting_language_complex)
                    .setListener((MenuDialog.OnListener<String>) (dialog, position, string) -> WebviewActivity.start(this, "http://106.14.105.101:88"))
                    .setGravity(Gravity.BOTTOM)
                    .setAnimStyle(AnimAction.ANIM_BOTTOM)
                    .show();
        });
        sbSettingCache = findViewById(R.id.sb_setting_cache);

    }

    @Override
    protected void initData() {
        sbSettingCache.setRightText(CacheDataManager.getTotalCacheSize(this));
    }


}