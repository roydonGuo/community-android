package com.roydon.community.ui.popup;

import android.content.Context;

import com.roydon.community.R;
import com.roydon.library.BasePopupWindow;

/**
 * desc   : 可进行拷贝的副本
 */
public final class CopyPopup {

    public static final class Builder extends BasePopupWindow.Builder<Builder> {

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.popup_copy);
        }
    }
}