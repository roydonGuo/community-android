package com.roydon.community.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;

import com.bumptech.glide.Glide;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.config.AppConfig;
import com.roydon.community.constants.CacheConstants;
import com.roydon.community.constants.Constants;
import com.roydon.community.manager.CacheDataManager;
import com.roydon.community.ui.dialog.MenuDialog;
import com.roydon.community.ui.dialog.MessageDialog;
import com.roydon.community.ui.dialog.UpdateAppDialog;
import com.roydon.community.utils.cache.SPUtils;
import com.roydon.library.BaseDialog;
import com.roydon.library.action.AnimAction;
import com.roydon.library.layout.SettingBar;

public class SettingActivity extends BaseActivity {
    private String TOOL_TITLE = "设置";

    /**
     * 语言切换
     */
    private SettingBar sbSettingLanguage;
    /**
     * 检测更新
     */
    private SettingBar sbSettingUpdate;
    /**
     * 关于我们
     */
    private SettingBar sbSettingAbout;

    /**
     * 缓存
     */
    private SettingBar sbSettingCache;

    /**
     * 退出登录
     */
    private SettingBar sbSettingExit;

    /**
     * 测试
     */
    private SettingBar sbTestTitleBar;

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
        sbSettingCache.setOnClickListener(v -> {
            new MessageDialog.Builder(this)
                    .setTitle("确定清空缓存吗")
                    .setMessage("本次操作只会清除缓存的图片以及其他资源")
                    .setConfirm(getString(R.string.common_confirm))
                    .setCancel(getString(R.string.common_cancel))
                    //.setAutoDismiss(false)// 设置点击按钮后不关闭对话框
                    .setListener(new MessageDialog.OnListener() {
                        @Override
                        public void onConfirm(BaseDialog dialog) {
                            // 清除内存缓存（必须在主线程）
                            Glide.get(SettingActivity.this).clearMemory();
                            new Thread(() -> {
                                // 清除本地缓存（必须在子线程）
                                Glide.get(SettingActivity.this).clearDiskCache();
                            }).start();
                            CacheDataManager.clearAllCache(SettingActivity.this);
                            postDelayed(() -> {
                                // 重新获取应用缓存大小
                                sbSettingCache.setRightText(CacheDataManager.getTotalCacheSize(SettingActivity.this));
                            }, 500);
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
                        }
                    }).show();
        });
        sbSettingUpdate = findViewById(R.id.sb_setting_update);
        sbSettingUpdate.setOnClickListener(v -> {
            // 本地的版本码和服务器的进行比较
            if (32 > AppConfig.getVersionCode()) {
                new UpdateAppDialog.Builder(this)
                        // 版本名
                        .setVersionName("1.2.0")
                        // 是否强制更新
                        .setForceUpdate(false)
                        // 更新日志
                        .setUpdateLog("修复Bug\n优化用户体验")
                        // 下载 url
                        .setDownloadUrl("https://...../AndroidProject.apk")
                        .show();
            } else {
                showShortToast("当前已是最新版本");
            }
        });
        sbSettingAbout = findViewById(R.id.sb_setting_about);
        sbSettingAbout.setOnClickListener(v -> {
            navigateTo(AboutUsActivity.class);
        });
        sbSettingExit = findViewById(R.id.sb_setting_exit);
        sbSettingExit.setOnClickListener(v -> {
            new MessageDialog.Builder(this)
                    .setTitle("确定退出登录吗")
                    .setMessage("确定清空内存并退出登录吗？")
                    .setConfirm(getString(R.string.common_confirm))
                    .setCancel(getString(R.string.common_cancel))
                    //.setAutoDismiss(false)// 设置点击按钮后不关闭对话框
                    .setListener(new MessageDialog.OnListener() {
                        @Override
                        public void onConfirm(BaseDialog dialog) {
                            SharedPreferences sp = getSharedPreferences(Constants.AUTHORIZATION, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.clear();
                            editor.apply();
                            // 清空缓存
                            SPUtils.remove(CacheConstants.USERID, SettingActivity.this);
                            SPUtils.remove(CacheConstants.USERNAME, SettingActivity.this);
                            SPUtils.remove(CacheConstants.USER_AVATAR, SettingActivity.this);
                            navigateToWithFlag(LoginActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
                        }
                    }).show();
        });
        sbTestTitleBar = findViewById(R.id.sb_test_title_bar);
        sbTestTitleBar.setOnClickListener(v->{
            navigateTo(TestTitlebarActivity.class);
        });

    }

    @Override
    protected void initData() {
        sbSettingCache.setRightText(CacheDataManager.getTotalCacheSize(this));
    }


}