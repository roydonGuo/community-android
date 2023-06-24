package com.roydon.community.fragment;

import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.roydon.community.R;
import com.roydon.community.adapter.BannerAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.AppBannerNotice;
import com.roydon.community.domain.vo.BannerNoticeListRes;
import com.roydon.community.view.SobViewPager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private ViewPager viewPager;
    private RefreshLayout refreshLayout;
    private SobViewPager sobViewPager;
    private ImageView picture;
    private LinearLayout mDotLayout, funcOne;
    private SlidingTabLayout slidingTabLayout;

    private BannerAdapter mBannerAdapter;
    private List<String> mUrls = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mBannerAdapter.setmUrls(mUrls);
                    mBannerAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    public static final int TAKE_PHOTO = 1;//声明一个请求码，用于识别返回的结果
    private Uri imageUri;
    private final String filePath = Environment.getExternalStorageDirectory() + File.separator + "output_image.jpg";

    @Override
    protected void initView() {
//        refreshLayout = mRootView.findViewById(R.id.refreshLayout);
        sobViewPager = mRootView.findViewById(R.id.sob_looper);
        funcOne = mRootView.findViewById(R.id.func_one);
        picture = mRootView.findViewById(R.id.picture);

        /**
         * MimeType.ofAll() -->全部类型
         * MimeType.ofImage() -->图片
         * MimeType.ofVideo() -->视频
         * maxSelectable  选择的最大数量
         */
//        Matisse.from(PhotoActivity.this)
//                .choose(MimeType.ofAll())
//                .countable(true)
//                .maxSelectable(9)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                .thumbnailScale(0.85f)
//                .imageEngine(new GlideEngine())
//                .showPreview(false)
//
//                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
//                .capture(true)
//                .captureStrategy(new CaptureStrategy(true, "PhotoPicker"))
//
//                //蓝色主题
//                // .theme(R.style.Matisse_Zhihu)
//                //黑色主题
//                .theme(R.style.Matisse_Dracula)
//                //Glide加载方式
//                .imageEngine(new GlideEngine())
//                //Picasso加载方式
//                // .imageEngine(new PicassoEngine())
//                //请求码
//                .forResult(REQUEST_CODE_CHOOSE);

    }

    @Override
    protected void initData() {

        mBannerAdapter = new BannerAdapter(getContext(), mUrls);
        sobViewPager.setAdapter(mBannerAdapter);

//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                getBannerNoticeList(true);
//            }
//        });
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
//                getBannerNoticeList(false);
//            }
//        });
        getBannerNoticeList(true);

    }

    /**
     * 轮播图集合
     */
    private void getBannerNoticeList(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", 1);
        params.put("pageSize", 5);
        params.put("showInApp", 1);
        Api.build(ApiConfig.BANNER_NOTICE_LIST, params).getRequestWithToken(getActivity(), new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
//                if (isRefresh) {
//                    refreshLayout.finishRefresh(true);
//                } else {
//                    refreshLayout.finishLoadMore(true);
//                }
                BannerNoticeListRes response = new Gson().fromJson(res, BannerNoticeListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<AppBannerNotice> list = response.getRows();
                    if (list != null && list.size() > 0) {
                        if (isRefresh) {
                            mUrls = list.stream().map(AppBannerNotice::getNoticeImgUrl).collect(Collectors.toList());
                            mHandler.sendEmptyMessage(0);
                        }
                    } else {
                        if (isRefresh) {
                            showShortToastSync("暂时无轮播数据");
                        } else {
                            showShortToastSync("没有更多数据");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}