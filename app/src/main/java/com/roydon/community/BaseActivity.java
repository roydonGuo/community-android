package com.roydon.community;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.roydon.community.constants.Constants;

/**
 * @author roydon
 * @date 2023/6/6 2:30
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setColorStatus(this, 0xFF3700B3);
        Window window = this.getWindow();
        /*如果之前是办透明模式，要加这一句需要取消半透明的Flag
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
//        getDelegate().setLocalNightMode(SPUtils.getTheme("theme", 1, this));
//        recreate();
        context = this;
        setContentView(initLayout());
        initView();
        initData();
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    // 短toast
    public void showShortToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    // 长toast
    public void showLongToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    // 短toast
    public void showSyncShortToast(String msg) {
        Looper.prepare();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    // 长toast
    public void showSyncLongToast(String msg) {
        Looper.prepare();
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Looper.loop();
    }

    //页面跳转
    public void navigateTo(Class clazz) {
        startActivity(new Intent(context, clazz));
    }

    public void navigateToWithFlag(Class clazz, int flags) {
        Intent in = new Intent(context, clazz);
        in.setFlags(flags);
        startActivity(in);
    }

    public void navigateToWithBundle(Class cls, Bundle bundle) {
        Intent in = new Intent(context, cls);
        in.putExtras(bundle);
        startActivity(in);
    }

    protected void insertVal(String key, String val) {
        SharedPreferences sp = getSharedPreferences(Constants.AUTHORIZATION, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.apply();
    }

    protected String findByKey(String key) {
        SharedPreferences sp = getSharedPreferences(Constants.AUTHORIZATION, MODE_PRIVATE);
        return sp.getString(key, "");
    }

}
