package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.ZoomMediaLoader;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.AppNews;
import com.roydon.community.domain.vo.AppNewsRes;
import com.roydon.community.utils.img.ImagePreviewLoader;
import com.roydon.community.utils.string.StringUtil;
import com.roydon.community.view.ImageViewInfo;
import com.roydon.community.view.CircleTransform;
import com.squareup.picasso.Picasso;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsDetailActivity extends BaseActivity {

    private TextView tvTitle, tvContent;
    private ImageView ivSourceAvatar, ivReturn;
    private String newsId;
    private WebView mWebView;

    @Override
    protected int initLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        ivSourceAvatar = findViewById(R.id.iv_source_avatar);
        ivReturn = findViewById(R.id.iv_return);
        ZoomMediaLoader.getInstance().init(new ImagePreviewLoader());
        // 实例化
//        mWebView = findViewById(R.id.webView);
    }

    @Override
    protected void initData() {
//        LoadingDialog.getInstance(this).show();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            newsId = bundle.getString("newsId");
            this.getNewsDetail(newsId);
        }
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getNewsDetail(String newsId) {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.NEWS_DETAIL + "/" + newsId, params).getRequest(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                AppNewsRes response = new Gson().fromJson(res, AppNewsRes.class);
                if (response != null && response.getCode() == 200) {
                    AppNews appNews = response.getData();
                    // 使用handler将数据传递给主线程
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = appNews;
                    mHandler.sendMessage(message);
                } else {
                    finish();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                AppNews appNews = (AppNews) msg.obj;
                Log.e("onSuccess", appNews.getNewsId());
                newsDetailShow(appNews);
//                LoadingDialog.getInstance(getApplicationContext()).dismiss();
            }
        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    private void newsDetailShow(AppNews appNews) {
        tvTitle.setText(appNews.getNewsTitle());
        tvContent.setText(appNews.getNewsContent());
        String newsContent = appNews.getNewsContent();
//        tvContent.setText(Html.fromHtml(newsContent, new ImageGetterUtils.MyImageGetter(this, tvContent), null));
        RichText.initCacheDir(context);
        // 设置为Html，设置图片点击预览大图
        RichText.fromHtml(newsContent)
                .autoPlay(true)
                .borderRadius(10)
                .scaleType(ImageHolder.ScaleType.fit_center)
                .imageClick(this::bigImageLoader)
                .into(tvContent);

        Picasso.with(this).load(appNews.getCoverImg()).transform(new CircleTransform()).into(ivSourceAvatar);
    }

    private List<ImageViewInfo> mImgPreviewLists = new ArrayList<>();  //预览的图片列表

    // 图片点击预览大图
    private void bigImageLoader(List<String> imageUrls, int position) {
//        Dialog dialog = new Dialog(this);
        if (StringUtil.isEmpty(mImgPreviewLists)) {
            for (int i = 0; i < imageUrls.size(); i++) {
                String url = imageUrls.get(i);
                mImgPreviewLists.add(new ImageViewInfo(url));
            }
        }
        GPreviewBuilder.from(this)
                .setData(mImgPreviewLists)//放入数据集合
                .setCurrentIndex(position)
                .setSingleFling(true)//是否在黑屏区域点击返回
                .setDrag(false)//是否禁用图片拖拽返回
                .setType(GPreviewBuilder.IndicatorType.Number)//指示器类型:dot,number
                .start();//启动
//        ImageView imageView = new ImageView(context);
        /**
         * scaleType=“matrix” 是保持原图大小、从左上角的点开始，以矩阵形式绘图。
         * scaleType=“fitXY” 是将原图进行横方向（即XY方向）的拉伸后绘制的。
         * scaleType=“fitStart” 是将原图沿左上角的点（即matrix方式绘图开始的点），按比例缩放原图绘制而成的。
         * scaleType=“fitCenter” 是将原图沿上方居中的点（即matrix方式绘图第一行的居中的点），按比例缩放原图绘制而成的。
         * scaleType=“fitEnd” 是将原图沿下方居中的点（即matrix方式绘图最后一行的居中的点），按比例缩放原图绘制而成的。
         * scaleType=“Center” 是保持原图大小，以原图的几何中心点和ImagView的几何中心点为基准，只绘制ImagView大小的图像。
         * scaleType=“centerCrop” 不保持原图大小，以原图的几何中心点和ImagView的几何中心点为基准，只绘制ImagView大小的图像（以填满ImagView为目标，对原图进行裁剪）。
         * scaleType=“centerInside” 不保持原图大小，以原图的几何中心点和ImagView的几何中心点为基准，只绘制ImagView大小的图像（以显示完整图片为目标，对原图进行缩放）。
         */
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        Glide.with(context).load(imgUrl).centerCrop().into(imageView);
        // 使用异步任务加载图片并显示在ImageView中
//        new LoadImageTask().execute(imgUrl, imageView);

        // 绑定xml组件
//        dialog.setContentView(R.layout.dialog_image_preview);
//
//        ImageView imageView = dialog.findViewById(R.id.dialog_image);
//        Glide.with(context).load(imgUrl).fitCenter().into(imageView);
////        new LoadImageTask().execute(imgUrl, imageView);
//        imageView.setScaleType(ImageView.ScaleType.MATRIX);
//        ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener(imageView));
//        imageView.setOnTouchListener((v, event) -> {
//            scaleGestureDetector.onTouchEvent(event);
//            return true;
//        });
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        RichText.clear(this);
    }
}