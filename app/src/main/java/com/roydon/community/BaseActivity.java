package com.roydon.community;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author roydon
 * @date 2023/6/6 2:30
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    protected void insertVal(String key, String val) {
        SharedPreferences sp = getSharedPreferences("sp_roydon", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }

    protected String findByKey(String key) {
        SharedPreferences sp = getSharedPreferences("sp_roydon", MODE_PRIVATE);
        return sp.getString(key, "");
    }


}
