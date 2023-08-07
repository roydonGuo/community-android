package com.roydon.community.utils;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class HtmlTextUtils {

    /**
     * 仅显示文本内容
     *
     * @param textView      目标控件
     * @param source        目标源
     */
    public static void showJustText(TextView textView, String source) {
        if (textView == null) {
            return;
        }
        // 溢出滚动
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        CharSequence charSequence;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            charSequence = Html.fromHtml(source,Html.FROM_HTML_MODE_LEGACY);
        } else {
            charSequence = Html.fromHtml(source);
        }
        textView.setText(charSequence);
    }
}
