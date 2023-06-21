package com.roydon.community.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.roydon.community.R;
import com.roydon.community.adapter.HomeAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.vo.NewsCategoryRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsFragment extends BaseLazyLoadFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        viewPager = mRootView.findViewById(R.id.fixedViewPager);
        slidingTabLayout = mRootView.findViewById(R.id.slidingTabLayout);
    }

    @Override
    protected void lazyLoad() {
        getNewsCategoryList();
    }

    @Override
    protected void visibleReLoad() {

    }

    @Override
    protected void inVisibleRelease() {

    }

    @Override
    protected void resume() {

    }

    @Override
    protected void pause() {

    }

    @Override
    protected void initData() {
//        getNewsCategoryList();
    }

    /**
     * 新闻分类集合
     */
    private void getNewsCategoryList() {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.NEWS_CATEGORY_LIST, params).getRequest(getActivity(), new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NewsCategoryRes response = new Gson().fromJson(res, NewsCategoryRes.class);
                        if (response != null && response.getCode() == 200) {
                            List<NewsCategoryRes.DataBean> data = response.getData();
                            if (data != null && data.size() > 0) {
                                mTitles = new String[data.size()];
                                for (int i = 0; i < data.size(); i++) {
                                    mTitles[i] = data.get(i).getDictLabel();
                                    mFragments.add(NewsAppFragment.newInstance(data.get(i).getDictValue()));
                                }
                                viewPager.setOffscreenPageLimit(mFragments.size());
                                viewPager.setAdapter(new HomeAdapter(getFragmentManager(), mTitles, mFragments));
                                slidingTabLayout.setViewPager(viewPager);
                            }
                        }
                    }
                });
            }
            @Override
            public void onFailure(Exception e) {
            }
        });
    }
}