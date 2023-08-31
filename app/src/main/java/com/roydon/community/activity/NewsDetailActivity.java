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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

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
import com.roydon.community.view.CircleTransform;
import com.roydon.community.view.ImageViewInfo;
import com.squareup.picasso.Picasso;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsDetailActivity extends BaseActivity {

    private ProgressBar loadingSpinner;

    private TextView tvNewsTitleTop, tvTitle, tvSource, tvContent;
    private ImageView ivSourceAvatar, ivReturn;
    private String newsId;
    private WebView mWebView;

    private Toolbar toolbar;

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                AppNews appNews = (AppNews) msg.obj;
                Log.e("onSuccess", appNews.getNewsId());
                newsDetailShow(appNews);
                // 显示加载动画
                loadingSpinner.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        tvNewsTitleTop = findViewById(R.id.tv_news_title_top);
        tvTitle = findViewById(R.id.tv_title);
        tvSource = findViewById(R.id.tv_source);
        tvContent = findViewById(R.id.tv_content);
        ivSourceAvatar = findViewById(R.id.iv_source_avatar);
        ivReturn = findViewById(R.id.iv_return);
        ZoomMediaLoader.getInstance().init(new ImagePreviewLoader());
//        mWebView = findViewById(R.id.webView);
        // 显示加载动画
        loadingSpinner = findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            newsId = bundle.getString("newsId");
            this.getNewsDetail(newsId);
        }
        ivReturn.setOnClickListener(v -> {
            finish();
        });
    }

    /**
     * 获取新闻详情
     *
     * @param newsId
     */
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

    @SuppressLint("SetJavaScriptEnabled")
    private void newsDetailShow(AppNews appNews) {
        tvNewsTitleTop.setText(appNews.getNewsTitle());
        tvTitle.setText(appNews.getNewsTitle());
        tvSource.setText(appNews.getSource());
        tvContent.setText(appNews.getNewsContent());
        String newsContent = appNews.getNewsContent();
//        tvContent.setText(Html.fromHtml(newsContent, new ImageGetterUtils.MyImageGetter(this, tvContent), null));
        RichText.initCacheDir(context);
        // 设置为Html，设置图片点击预览大图
        RichText.fromHtml(newsContent).autoPlay(true).borderRadius(10).scaleType(ImageHolder.ScaleType.fit_center).imageClick(this::bigImageLoader).into(tvContent);

        Picasso.with(this).load(appNews.getCoverImg()).transform(new CircleTransform()).into(ivSourceAvatar);
    }

    private List<ImageViewInfo> mImgPreviewLists = new ArrayList<>();  //预览的图片列表

    // 图片点击预览大图
    private void bigImageLoader(List<String> imageUrls, int position) {
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
                .start();
    }

    @Override
    protected void onDestroy() {
        RichText.clear(this);
        super.onDestroy();
    }
}