package com.roydon.community.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @author roydon
 * @date 2023/6/19 11:36
 */
public class NoScrollFixedViewPager extends ViewPager {

    public NoScrollFixedViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollFixedViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

    /**
     * return false;禁止滑动切换
     *
     * @param ev The motion event.
     * @return false
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * return false;禁止滑动切换
     *
     * @param ev The motion event being dispatched down the hierarchy.
     * @return false
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
