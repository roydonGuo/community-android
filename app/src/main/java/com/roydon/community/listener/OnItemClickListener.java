package com.roydon.community.listener;

import android.view.View;

public interface OnItemClickListener {
    /**
     * 当RecyclerView某个被点击的时候回调
     *
     * @param view     点击item的视图
     * @param position 点击得到的数据
     */
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}