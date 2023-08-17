package com.roydon.community.activity;

import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.adapter.MyPagerAdapter;
import com.roydon.community.domain.entity.TabEntity;
import com.roydon.community.fragment.HomeFragment;
import com.roydon.community.fragment.MallFragment;
import com.roydon.community.fragment.MyFragment;
import com.roydon.community.fragment.NewsFragment;

import java.util.ArrayList;

/**
 * @author roydon
 */
public class HomeActivity extends BaseActivity {

    private String[] mTitles = {"首页", "新闻", "商城", "我的"};
    private int[] mIconUnselectIds = {R.mipmap.tab_home_unselect, R.mipmap.tab_news_unselect, R.mipmap.tab_mall_unselect, R.mipmap.tab_my_unselect};
    private int[] mIconSelectIds = {R.mipmap.tab_home_select, R.mipmap.tab_news_select, R.mipmap.tab_mall_select, R.mipmap.tab_my_select};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ViewPager viewPager;
    private CommonTabLayout commonTabLayout;

    //第一次点击退出事件发生的时间
    private long mExitTime;

    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        // 解决弹出输入法会把底部导航栏顶上来的bug
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.commonTabLayout);
    }

    @Override
    protected void initData() {
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(NewsFragment.newInstance());
        mFragments.add(MallFragment.newInstance());
        mFragments.add(MyFragment.newInstance());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));
    }

    /**
     * 点击两次退出app
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出APP", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}