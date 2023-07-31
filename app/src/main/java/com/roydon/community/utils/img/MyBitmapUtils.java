package com.roydon.community.utils.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 自定义图片加载工具
 * 作者：created by fanHongJiao
 */
public class MyBitmapUtils {
    private NetCacheUtils mNetCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private Context mContext;

    public MyBitmapUtils(Context context) {
        this.mContext = context;
        mLocalCacheUtils = new LocalCacheUtils(context);
        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils);
    }

    /**
     * @param ivPic imageView
     * @param url   图片地址
     * @return
     */
    public Bitmap disPlay(ImageView ivPic, String url) {
        //本地缓存
        Bitmap bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            return bitmap;
        }
        //网络加载图片，在没有缓存的情况下去请求网络图片，并将图片保存在本地文件夹
        mNetCacheUtils.getBitmapFromNet(ivPic, url);
        return bitmap;

    }
}