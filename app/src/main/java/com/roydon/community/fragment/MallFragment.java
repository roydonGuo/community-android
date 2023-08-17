package com.roydon.community.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.R;
import com.roydon.community.action.StatusAction;
import com.roydon.community.activity.CartActivity;
import com.roydon.community.activity.GoodDetailActivity;
import com.roydon.community.adapter.MallGoodAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.MallGoodsVO;
import com.roydon.community.domain.vo.MallGoodsRes;
import com.roydon.community.widget.HintLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MallFragment extends BaseLazyLoadFragment implements StatusAction {

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_WHAT_GOODS_LIST = 1;

    private RefreshLayout refreshLayout;
    private RecyclerView rvMallGoods;
    private int pageNum = 1;
    private MallGoodAdapter mallGoodAdapter;
    private List<MallGoodsVO> goodsList = new ArrayList<>();
    private ImageView ivMallCart;

    private HintLayout mHintLayout;

    @Override
    protected void lazyLoad() {
        // 首页瀑布流列表
        mallGoodAdapter = new MallGoodAdapter(getActivity());
        rvMallGoods.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvMallGoods.setAdapter(mallGoodAdapter);
        mallGoodAdapter.setOnItemClickListener(new MallGoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                MallGoodsVO mallGoods = goodsList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("goodsId", mallGoods.getGoodsId());
                navigateToWithBundle(GoodDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getMallGoodsList(true);
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            pageNum++;
            getMallGoodsList(false);
        });
        getMallGoodsList(true);
        ivMallCart.setOnClickListener(v -> {
            navigateTo(CartActivity.class);
        });
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

    public MallFragment() {
    }

    public static MallFragment newInstance() {
        return new MallFragment();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_WHAT_EMPTY:
                    showEmpty();
                    break;
                case HANDLER_WHAT_GOODS_LIST:
                    mallGoodAdapter.setData(goodsList);
                    mallGoodAdapter.notifyDataSetChanged();
                    showComplete();
                    break;
            }
        }
    };

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void initView() {
        rvMallGoods = mRootView.findViewById(R.id.rv_mall_goods);
        refreshLayout = mRootView.findViewById(R.id.rl_goods);
        ivMallCart = mRootView.findViewById(R.id.iv_mall_cart);
        mHintLayout = mRootView.findViewById(R.id.hl_status_hint);
        showLoading();
    }

    @Override
    protected void initData() {

    }

    private void getMallGoodsList(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE_20);
        // 上架商品
        params.put("status", "0");
        Api.build(ApiConfig.MALL_GOODS_LIST, params).postRequestWithToken(getActivity(), new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("getMallGoodsList", res);
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                // 后端传递时间格式解析
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                MallGoodsRes mallGoodsRes = gson.fromJson(res, MallGoodsRes.class);
                if (mallGoodsRes != null && mallGoodsRes.getCode() == 200) {
                    List<MallGoodsVO> mallGoodsList = mallGoodsRes.getData();
                    if (mallGoodsList != null && mallGoodsList.size() > 0) {
                        if (isRefresh) {
                            goodsList = mallGoodsList;
                        } else {
                            goodsList.addAll(mallGoodsList);
                        }
                        mHandler.sendEmptyMessage(HANDLER_WHAT_GOODS_LIST);
                    } else {
                        if (isRefresh) {
                            Log.e("getMallGoodsList", "暂时无数据");
                            mHandler.sendEmptyMessage(HANDLER_WHAT_EMPTY);
                        } else {
                            Log.e("getMallGoodsList", "没有更多数据");
                            showShortToastSync("没有更多数据");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                mHandler.sendEmptyMessage(HANDLER_WHAT_EMPTY);
            }
        });
    }

}