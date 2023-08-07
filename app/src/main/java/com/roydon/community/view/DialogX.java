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
import android.widget.TextView;

import com.roydon.community.R;
import com.roydon.community.listener.OnConfirmDialogClickListener;
import com.roydon.community.listener.OnPhotoSelectDialogClickListener;
import com.roydon.community.listener.OnShareDialogClickListener;

public class DialogX {

    /**
     * 弹出分享框
     *
     * @param context
     * @param listener
     */
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

    /**
     * 图片选择框
     *
     * @param context
     * @param listener
     */
    public static void showPhotoSelectDialog(Context context, final OnPhotoSelectDialogClickListener listener) {
        View dialogPhotoSelect = LayoutInflater.from(context).inflate(R.layout.layout_dialog_photo_select, null);
        final Dialog dialog = new Dialog(context, R.style.DialogShare);
        dialog.setContentView(dialogPhotoSelect);
        dialog.show();
        // 点击监听
        View.OnClickListener clickListener = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.take_photo:
                        if (listener != null) {
                            listener.onSelectPhoto();
                        }
                        break;
                    case R.id.from_albums:
                        if (listener != null) {
                            listener.onSelectAlbums();
                        }
                        break;
                    case R.id.cancel:
                        if (listener != null) {
                            listener.onSelectCancel();
                        }
                        break;
                    default:
                        dialog.dismiss();
                        break;
                }
                dialog.dismiss();
            }
        };
        TextView takePhoto = dialogPhotoSelect.findViewById(R.id.take_photo);
        TextView fromAlbums = dialogPhotoSelect.findViewById(R.id.from_albums);
        TextView cancel = dialogPhotoSelect.findViewById(R.id.cancel);

        takePhoto.setOnClickListener(clickListener);
        fromAlbums.setOnClickListener(clickListener);
        cancel.setOnClickListener(clickListener);

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

    }

    /**
     * 弹出单条内容编辑框
     *
     * @param context
     * @param listener
     */
    public static void showEditTextDialog(Context context, final OnConfirmDialogClickListener listener) {
        View dialogLayout = LayoutInflater.from(context).inflate(R.layout.layout_dialog_edit, null);
        final Dialog dialog = new Dialog(context, R.style.DialogShare);
        dialog.setContentView(dialogLayout);
        dialog.show();
        // 点击监听
        View.OnClickListener clickListener = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_confirm:
                        if (listener != null) {
                            listener.onConfirm();
                        }
                        break;
                    case R.id.btn_cancel:
                        if (listener != null) {
                            listener.onCancel();
                        }
                        break;
                    default:
                        dialog.dismiss();
                        break;
                }
                dialog.dismiss();
            }
        };
        Button confirm = dialogLayout.findViewById(R.id.btn_confirm);
        Button cancel = dialogLayout.findViewById(R.id.btn_cancel);

        confirm.setOnClickListener(clickListener);
        cancel.setOnClickListener(clickListener);

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

    }
}
