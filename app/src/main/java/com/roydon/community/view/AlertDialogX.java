package com.roydon.community.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roydon.community.R;

/**
 * 自定义弹出操作框
 */
public class AlertDialogX {

    /**
     * 默认的弹出窗口
     */
    public static void alertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("弹出窗");
        builder.setMessage("提示信息！");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("确认执行函数");
            }
        }).setNegativeButton("取消", null);
        builder.show();
    }

//    public static void showDeleteConfirmationDialog(Context context, String title, String message, final DialogInterface.OnClickListener positiveClickListener) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(title).setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (positiveClickListener != null) {
//                    positiveClickListener.onClick(dialogInterface, i);
//                }
//            }
//        }).setNeutralButton("取消", null).create().show();
//    }

    /**
     * 自定义样式的弹出窗
     */
    public static void showDeleteConfirmationDialog(Context context, String title, String msg, final DialogInterface.OnClickListener positiveClickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 自定义 title样式
        TextView tvTitle = new TextView(context);
        tvTitle.setText(title);
        tvTitle.setTextSize(20);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setPadding(0, 20, 0, 20);
        builder.setCustomTitle(tvTitle);
        // 中间的信息以一个view的形式设置进去
        TextView tvMsg = new TextView(context);
        tvMsg.setText(msg);
        tvMsg.setTextSize(18);
        tvMsg.setGravity(Gravity.CENTER);
        tvMsg.setPadding(20, 20, 20, 20);
        builder.setView(tvMsg);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (positiveClickListener != null) {
                    positiveClickListener.onClick(dialogInterface, i);
                }
            }
        }).setNegativeButton("取消", null);
        // 调用 show()方法后得到 dialog对象
        AlertDialog dialog = builder.show();
        final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        final Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams positiveParams = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        positiveParams.gravity = Gravity.CENTER;
        positiveParams.setMargins(10, 10, 10, 10);
        positiveParams.width = 0;
        // 安卓下面有三个位置的按钮，默认权重为 1,设置成 500或更大才能让两个按钮看起来均分
        positiveParams.weight = 500;
        LinearLayout.LayoutParams negativeParams = (LinearLayout.LayoutParams) negativeButton.getLayoutParams();
        negativeParams.gravity = Gravity.CENTER;
        negativeParams.setMargins(10, 10, 10, 10);
        negativeParams.width = 0;
        negativeParams.weight = 500;
        positiveButton.setLayoutParams(positiveParams);
        negativeButton.setLayoutParams(negativeParams);
        positiveButton.setBackgroundColor(Color.parseColor("#409EFF"));
        positiveButton.setTextColor(Color.parseColor("#FFFFFF"));
        negativeButton.setBackgroundColor(Color.parseColor("#DDDDDD"));
    }

    /**
     * 自定义样式的弹出窗
     */
    public static void showCustomAlertDialog(Context context, String title, String message, View.OnClickListener positiveClickListener, View.OnClickListener negativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_custom_alert, null);

        builder.setView(dialogView);

        // 获取对话框中的视图组件
        TextView titleTextView = dialogView.findViewById(R.id.dialog_title);
        TextView messageTextView = dialogView.findViewById(R.id.dialog_msg);
        Button positiveButton = dialogView.findViewById(R.id.dialog_positive_button);
        Button negativeButton = dialogView.findViewById(R.id.dialog_negative_button);

        // 设置对话框内容和点击事件
        titleTextView.setText(title);
        messageTextView.setText(message);

        AlertDialog alertDialog = builder.create();

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positiveClickListener != null) {
                    positiveClickListener.onClick(view);
                }
                // 关闭对话框
                alertDialog.dismiss();
            }
        });

        if (negativeClickListener != null) {
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    negativeClickListener.onClick(view);
                    // 关闭对话框
                    alertDialog.dismiss();
                }
            });
        } else {
            negativeButton.setVisibility(View.GONE);
        }

        // 创建并显示对话框
        alertDialog.show();
    }


}
