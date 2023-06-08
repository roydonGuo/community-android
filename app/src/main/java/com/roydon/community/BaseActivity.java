package com.roydon.community;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author roydon
 * @date 2023/6/6 2:30
 */
public class BaseActivity extends AppCompatActivity {

    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    // 短toast
    public void showShortToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    // 长toast
    public void showLongToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
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
        SharedPreferences sp = getSharedPreferences("sp_user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }

    protected String findByKey(String key) {
        SharedPreferences sp = getSharedPreferences("sp_user", MODE_PRIVATE);
        return sp.getString(key, "");
    }


}
