package com.roydon.community.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.roydon.community.utils.img.MyBitmapUtils;

import java.util.List;

public class BannerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mUrls;
    private MyBitmapUtils myBitmapUtils;

    public BannerAdapter() {
    }

    public BannerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BannerAdapter(Context context, List<String> urls) {
        mContext = context;
        mUrls = urls;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<String> getmUrls() {
        return mUrls;
    }

    public void setmUrls(List<String> mUrls) {
        this.mUrls = mUrls;
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        // 让图片填充至整个控件，只需要添加centerCrop()
//        Glide.with(mContext).load(mUrls.get(position)).centerCrop().into(imageView);
        myBitmapUtils = new MyBitmapUtils(mContext);
        Bitmap imageFile = myBitmapUtils.disPlay(imageView, mUrls.get(position));
        imageView.setImageBitmap(imageFile);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        // 图片加载过程中显示指定以图片
//        Glide.with(context).load(url).centerCrop().placeholder(R.mipmap.defaultpic).into(imageView);
        // 图片加载失败同理，使用error();
//        Glide.with(context).load(url).centerCrop().error(R.mipmap.defaultpic).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
