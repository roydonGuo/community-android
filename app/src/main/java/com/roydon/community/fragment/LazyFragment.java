package com.roydon.community.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * http://www.cnblogs.com/dasusu/p/6745032.html
 * Viewpager + Fragment情况下，fragment的生命周期因Viewpager的缓存机制而失去了具体意义
 * 所以，我们在Fragment的生命周期中构造新的回调方法
 * 当需要懒加载的时候可以继承 LazyFragment
 */
public abstract class LazyFragment extends Fragment {

    protected String TAG = LazyFragment.class.getSimpleName() + this.toString();

    private boolean isLoaded = false; // 是否已经加载过数据
    private boolean isViewCreated = false; // 是否已经创建视图
    private boolean isVisibleToUser = false; // 是否对用户可见

    protected View mRootView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(initLayout(), container, false);
            initView();
        }
        unbinder = ButterKnife.bind(this, mRootView);
        isViewCreated = true;
        lazyLoad();
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        lazyLoad();
    }

    private void lazyLoad() {
        if (isViewCreated && isVisibleToUser && !isLoaded) {
            initData();
            isLoaded = true;
        }
    }
    protected abstract int initLayout();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 加载数据
     */
    protected abstract void initData();

}