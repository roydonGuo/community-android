package com.roydon.community.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseLazyLoadFragment extends BaseFragment {

    protected String TAG = BaseLazyLoadFragment.class.getSimpleName() + this.toString();

    //布局是否初始化完成
    private boolean isLayoutInitialized = false;
    //懒加载完成
    private boolean isLazyLoadFinished = false;
    //记录页面可见性
    private boolean isVisibleToUser = false;
    //不可见时释放部分资源
    private boolean isInVisibleRelease = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName() + "  onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, getClass().getSimpleName() + "  onCreateView");
        initView();
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, getClass().getSimpleName() + "  onDestroyView");

        //页面释放后，重置布局初始化状态变量
        isLayoutInitialized = false;
        this.mRootView = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName() + "  onActivityCreated");
        //此方法是在第一次初始化时onCreateView之后触发的
        //onCreateView和onActivityCreated中分别应该初始化哪些数据可以参考：
        //https://stackoverflow.com/questions/8041206/android-fragment-oncreateview-vs-onactivitycreated
        isLayoutInitialized = true;
        //第一次初始化后需要处理一次可见性事件
        //因为第一次初始化时setUserVisibleHint方法的触发要先于onCreateView
        dispatchVisibleEvent();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, getClass().getSimpleName() + "  onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, getClass().getSimpleName() + "  onResume");

        //页面从其他Activity返回时，重新加载被释放的资源
        if (isLazyLoadFinished && isLayoutInitialized && isInVisibleRelease && isVisibleToUser) {
//            visibleReLoad();

            resume();

            isInVisibleRelease = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, getClass().getSimpleName() + "  onPause");

        //当从Fragment切换到其他Activity释放部分资源
        if (isLazyLoadFinished && isVisibleToUser) {
            //页面从可见切换到不可见时触发，可以释放部分资源，配合reload方法再次进入页面时加载
//            inVisibleRelease();

            pause();
            isInVisibleRelease = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, getClass().getSimpleName() + "  onDestroy");

        //重置所有数据
        this.mRootView = null;
        isLayoutInitialized = false;
        isLazyLoadFinished = false;
        isVisibleToUser = false;
        isInVisibleRelease = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, getClass().getSimpleName() + "  setUserVisibleHint isVisibleToUser = " + isVisibleToUser);

        dispatchVisibleEvent();
    }

    /**
     * 处理可见性事件
     */
    private void dispatchVisibleEvent() {
        Log.d(TAG, getClass().getSimpleName() + "  dispatchVisibleEvent isVisibleToUser = " + getUserVisibleHint() + " --- isLayoutInitialized = " + isLayoutInitialized + " --- isLazyLoadFinished = " + isLazyLoadFinished);

        if (getUserVisibleHint() && isLayoutInitialized) {
            //可见
            if (!isLazyLoadFinished) {
                //第一次可见，懒加载
                lazyLoad();
                isLazyLoadFinished = true;
            } else {
                //非第一次可见，刷新数据
                visibleReLoad();
            }
        } else {
            if (isLazyLoadFinished && isVisibleToUser) {
                //页面从可见切换到不可见时触发，可以释放部分资源，配合reload方法再次进入页面时加载
                inVisibleRelease();
            }
        }

        //处理完可见性事件之后修改isVisibleToUser状态
        this.isVisibleToUser = getUserVisibleHint();
    }

    /**
     * 绑定布局
     *
     * @return 布局ID
     */
    protected abstract int initLayout();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 懒加载<br/>
     * 只会在初始化后第一次可见时调用一次。
     */
    protected abstract void lazyLoad();

    /**
     * 刷新数据加载<br/>
     * 配合{@link #lazyLoad()}，在页面非第一次可见时刷新数据
     * 左右切换Fragment时触发
     */
    protected abstract void visibleReLoad();

    /**
     * 当页面从可见变为不可见时，释放部分数据和资源。<br/>
     * 比如页面播放器的释放或者一些特别占资源的数据的释放
     * 左右切换Fragment时触发
     */
    protected abstract void inVisibleRelease();

    /**
     * 当从其他页面返回，重新可见
     */
    protected abstract void resume();

    /**
     * 进入其他页面触发
     */
    protected abstract void pause();

}
