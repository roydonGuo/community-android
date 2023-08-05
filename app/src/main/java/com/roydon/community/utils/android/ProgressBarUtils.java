package com.roydon.community.utils.android;

import android.view.View;
import android.widget.ProgressBar;

import com.roydon.community.R;

/**
 * 数据加载进度条工具类
 */
public class ProgressBarUtils {

    public static void progressVisible(View view) {
        ProgressBar loadingSpinner = view.findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(View.VISIBLE);
    }

    public static void progressGone(View view) {
        ProgressBar loadingSpinner = view.findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(View.GONE);
    }
}
