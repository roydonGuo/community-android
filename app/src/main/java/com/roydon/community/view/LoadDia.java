package com.roydon.community.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.roydon.community.R;

public class LoadDia extends Dialog {

    public LoadDia(@NonNull Context context, int themeResId) {
        super(context, R.style.MyDialogStyle);
    }
 
    protected LoadDia(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_view);
        setCancelable(true);//设置点击dialog外部 不可取消
        setCanceledOnTouchOutside(true);//设置点击dialog外部 不可取消
    }
 
}