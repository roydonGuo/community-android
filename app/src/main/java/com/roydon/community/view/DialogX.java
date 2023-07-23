package com.roydon.community.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.roydon.community.R;
import com.roydon.community.listener.OnShareDialogClickListener;

public class DialogX {

    public static void showShareDialog(Context context, final OnShareDialogClickListener listener) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_share, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(context, R.style.DialogShare);
        dialog.setContentView(view);
        dialog.show();

        // 监听
        View.OnClickListener clickListener = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.share_to_wechat:
                        if (listener != null) {
                            listener.onShareToWechat();
                        }
                        break;
                    case R.id.share_to_pengyouquan:
                        if (listener != null) {
                            listener.onShareToPengyouquan();
                        }
                        break;
                    case R.id.share_to_qq:
                        if (listener != null) {
                            listener.onShareToQQ();
                        }
                        break;
                    case R.id.share_to_weibo:
                        if (listener != null) {
                            listener.onShareToWeibo();
                        }
                        break;
                    case R.id.btn_cancel_share:
                        if (listener != null) {
                            listener.onCancelShare();
                        }
                        break;
                }
                dialog.dismiss();
            }
        };

        ViewGroup mViewWechat = view.findViewById(R.id.share_to_wechat);
        ViewGroup mViewPengyouquan = view.findViewById(R.id.share_to_pengyouquan);
        ViewGroup mViewQQ = view.findViewById(R.id.share_to_qq);
        ViewGroup mViewWeibo = view.findViewById(R.id.share_to_weibo);
        Button mBtnCancel = view.findViewById(R.id.btn_cancel_share);

        mViewWechat.setOnClickListener(clickListener);
        mViewPengyouquan.setOnClickListener(clickListener);
        mViewQQ.setOnClickListener(clickListener);
        mViewWeibo.setOnClickListener(clickListener);
        mBtnCancel.setOnClickListener(clickListener);

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

    }
}
