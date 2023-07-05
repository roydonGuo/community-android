package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.AppNews;
import com.roydon.community.domain.vo.AppNewsRes;
import com.roydon.community.utils.LoadImageTask;
import com.roydon.community.view.CircleTransform;
import com.squareup.picasso.Picasso;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import java.util.HashMap;

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
                .imageClick((imageUrls, position) -> bigImageLoader(imageUrls.get(position)))
                .into(tvContent);
        Picasso.with(this).load(appNews.getCoverImg()).transform(new CircleTransform()).into(ivSourceAvatar);
    }

    private void bigImageLoader(String imgUrl) {
        Dialog dialog = new Dialog(this);
        ImageView imageView = new ImageView(context);
        Glide.with(context).load(imgUrl).centerCrop().into(imageView);
        // 使用异步任务加载图片并显示在ImageView中
        new LoadImageTask().execute(imgUrl, imageView);
        dialog.setContentView(imageView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        RichText.clear(this);
    }
}