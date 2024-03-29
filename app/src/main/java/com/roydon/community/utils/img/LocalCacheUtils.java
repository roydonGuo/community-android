package com.roydon.community.utils.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author roydon
 * 图片本地缓存工具类
 */
public class LocalCacheUtils {
    private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/community";
    private Context mContext;

    public LocalCacheUtils(Context context) {
        this.mContext = context;
    }

    public String getDiskCachePath(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getAbsolutePath();
        } else {
            cachePath = context.getCacheDir().getAbsolutePath();
        }
        return cachePath;
    }

    /**
     * 从本地读取图片
     *
     * @param url
     */
    public Bitmap getBitmapFromLocal(String url) {
        String fileName = null; // 把图片的url当做文件名,并进行MD5加密
        try {
            fileName = url;
            File file = new File(getDiskCachePath(mContext) + "/file/" + fileName); // 文件保存路径
            Bitmap bitmap = null;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            if (!bitmap.equals(null)) {
                return bitmap;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从网络获取图片后,保存至本地缓存
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapToLocal(String url, Bitmap bitmap) {
        try {
            String fileName = url; // 把图片的url当做文件名,并进行MD5加密
            File file = new File(getDiskCachePath(mContext) + "/file/" + fileName); // 文件保存路径
            // 通过得到文件的父文件,判断父文件是否存在
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}