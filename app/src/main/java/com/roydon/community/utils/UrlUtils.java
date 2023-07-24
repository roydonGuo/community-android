package com.roydon.community.utils;

import android.util.Log;
import android.widget.TextView;

public class UrlUtils {

    private static final String TAG = "UrlUtils";


    public static final String HTTP = "http";
    public static final String URL_PREFIX = "http://";
    public static final String URL_PREFIXs = "https://";
    public static final String URL_STAFFIX = URL_PREFIX;
    public static final String URL_STAFFIXs = URL_PREFIXs;

    /**
     * 获取string,为null则返回""
     *
     * @param tv
     * @return
     */
    public static String get(TextView tv) {
        if (tv == null || tv.getText() == null) {
            return "";
        }
        return tv.getText().toString();
    }

    /**
     * 判断字符类型是否是网址
     *
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        if (StringUtil.isEmpty(url)) {
            return false;
        } else if (!url.startsWith(URL_PREFIX) && !url.startsWith(URL_PREFIXs)) {
            return false;
        }
        return true;
    }

    /**
     * 获取网址，自动补全
     *
     * @param tv
     * @return
     */
    public static String getCorrectUrl(TextView tv) {
        return getCorrectUrl(get(tv));
    }

    /**
     * 获取网址，自动补全
     *
     * @param url
     * @return
     */
    public static String getCorrectUrl(String url) {
        Log.i(TAG, "getCorrectUrl : \n" + url);
        if (StringUtil.isEmpty(url)) {
            return "";
        }
        return isUrl(url) ? url : URL_PREFIX + url;
    }
}
