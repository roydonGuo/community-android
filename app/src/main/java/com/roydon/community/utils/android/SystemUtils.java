package com.roydon.community.utils.android;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class SystemUtils {

    /**
     * 复制到粘贴板
     *
     * @param context
     * @param msg
     */
    public static void copyToClipboard(Context context, String msg) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        // 将数据复制到新的 ClipData 对象,Set the clipboard's primary clip.
        clipboard.setPrimaryClip(ClipData.newPlainText(null, msg));
    }
}
