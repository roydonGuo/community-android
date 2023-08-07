//package com.roydon.community.layout;
//
//import android.content.Context;
//import android.database.DataSetObserver;
//import android.graphics.Color;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import com.roydon.community.R;
//import com.roydon.community.utils.SizeUtils;
//
//public class SobLooperView extends LinearLayout {
//
//    private ViewPager mViewPager;
//    private TextView mTitleView;
//    private LinearLayout mPointContainer;
//    private TitleBindListener mTitleBindListener = null;
//    private InnerPageAdapter mInnerAdapter = null;
//
//    public SobLooperView(Context context) {
//        //确保统一入口
//        this(context,null);
//    }
//
//    public SobLooperView(Context context,@Nullable AttributeSet attrs) {
//        //确保统一入口
//        this(context,attrs,0);
//    }
//
//    public SobLooperView(Context context,@Nullable AttributeSet attrs,int defStyleAttr) {
//        super(context,attrs,defStyleAttr);
//        //ViewPager
//        //TextView
//        //点容器，点需要动态地创建，因为点的个数跟内容个数有关系，同学们现在明白第三个参数为什么填写true了吧。
//        //填写true的话，就是自动填充到前面的viewGroup里
//        LayoutInflater.from(context).inflate(R.layout.layout_looper_view,this,true);
//        //等价于如下：
//        //View content = LayoutInflater.from(context).inflate(R.layout.layout_looper_view,this,false);
//        //addView(content);
//        initView();
//        initEvent();
//    }
//
//    /**
//     * 设置相关事件
//     */
//    private void initEvent() {
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            /**
//             *
//             * @param position position有两个情况，position positionOffset 为0时，就是当前的Position
//             *                 如果有滑动，position则会是下一个准备看到的position
//             *
//             * @param positionOffset 位置偏移量，取值为0到1，[0，1）
//             * @param positionOffsetPixels 位置偏移量，这个是像素界别的
//             */
//            @Override
//            public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels) {
//                //滑动时的回调
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                //滑动以后停下来的回调，position指所停在的位置
//                //这个时候我们去获取标题
//                if(mTitleBindListener != null && mInnerAdapter != null) {
//                    String title = mTitleBindListener.getTitle(position % mInnerAdapter.getDataSize());
//                    mTitleView.setText(title);
//                }
//                updateIndicator();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                //滑动状态的改变，有停止的，滑动中的.
//                //ViewPager#SCROLL_STATE_IDLE
//                //ViewPager#SCROLL_STATE_DRAGGING
//                //ViewPager#SCROLL_STATE_SETTLING
//            }
//        });
//    }
//
//    /**
//     * 找到子控件
//     */
//    private void initView() {
//        mViewPager = this.findViewById(R.id.content_pager);
//        mViewPager.setPageMargin(SizeUtils.dip2px(getContext(),20));
//        mViewPager.setOffscreenPageLimit(3);
//        mTitleView = this.findViewById(R.id.content_title);
//        mPointContainer = this.findViewById(R.id.content_point_container);
//    }
//
//
//    //提供方法给外部设置适配器进来，这个适配器怎么我们有规定，所以使用了一个抽象类来描述。
//    //而TitleBindListener用来获取标题，我们要标题的时候调用即可。
//    public void setData(InnerPageAdapter innerPageAdapter,TitleBindListener listener) {
//        mViewPager.setAdapter(innerPageAdapter);
//        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 + 1);
//        this.mInnerAdapter = innerPageAdapter;
//        this.mTitleBindListener = listener;
//        if(mTitleBindListener != null) {
//            String title = mTitleBindListener.getTitle(0);
//            mTitleView.setText(title);
//        }
//        //创建圆点
//        updateIndicator();
//        innerPageAdapter.registerDataSetObserver(new DataSetObserver() {
//            @Override
//            public void onChanged() {
//                updateIndicator();
//            }
//        });
//    }
//
//    private void updateIndicator() {
//        if(mInnerAdapter != null) {
//            //先删除
//            mPointContainer.removeAllViews();
//            int indicatorSize = mInnerAdapter.getDataSize();
//            for(int i = 0; i < indicatorSize; i++) {
//                View view = new View(getContext());
//                if((mViewPager.getCurrentItem() % mInnerAdapter.getDataSize() == i)) {
//                    view.setBackgroundColor(Color.parseColor("#ff0000"));
//                } else {
//                    view.setBackgroundColor(Color.parseColor("#ffffff"));
//                }
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(SizeUtils.dip2px(getContext(),5),SizeUtils.dip2px(getContext(),5));
//                layoutParams.setMargins(SizeUtils.dip2px(getContext(),5),0,SizeUtils.dip2px(getContext(),5),0);
//                view.setLayoutParams(layoutParams);
//                //添加到容器里
//                mPointContainer.addView(view);
//            }
//        }
//    }
//
//    public interface TitleBindListener {
//        String getTitle(int position);
//    }
//
//    public static abstract class InnerPageAdapter extends PagerAdapter {
//
//        public abstract int getDataSize();
//
//        @Override
//        public int getCount() {
//            //因为要无限轮播嘛，所以我们就给一个IntegerMaxValue
//            return Integer.MAX_VALUE;
//        }
//
//        @Override
//        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//            return view == object;
//        }
//
//        @NonNull
//        @Override
//        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            //载入view，至于显示什么view,不用管，由外面给进来。只要对position进行一个转换
//            int itemPosition = position % getDataSize();
//            View itemView = getItemView(container,itemPosition);
//            container.addView(itemView);
//            return itemView;
//        }
//
//        //至于要什么view，我不管，由外面给我。
//        protected abstract View getItemView(ViewGroup container,int itemPosition);
//
//        @Override
//        public void destroyItem(@NonNull ViewGroup container,int position,@NonNull Object object) {
//            container.removeView((View) object);
//        }
//    }
//
//}