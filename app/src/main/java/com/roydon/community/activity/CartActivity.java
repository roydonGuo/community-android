package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.action.StatusAction;
import com.roydon.community.adapter.CartAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.MallUserCartVO;
import com.roydon.community.domain.response.BaseResponse;
import com.roydon.community.domain.vo.MallUserCartListRes;
import com.roydon.community.view.AlertDialogX;
import com.roydon.community.utils.DoubleUtils;
import com.roydon.community.widget.HintLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CartActivity extends BaseActivity implements StatusAction {
    private String TOOLBAR_TITLE = "购物车";

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_REFRESH_CART_LIST = 1;

    private RefreshLayout refreshLayout;
    private RecyclerView rvMallCart;
    private LinearLayoutManager linearLayoutManager;
    private int pageNum = 1;
    private CartAdapter cartAdapter;

    private List<MallUserCartVO> cartList = new ArrayList<>();

    private TextView tvGoodTitle, tvGoodPrice, totalPrice;
    private Button btnGoodsCount, createOrder;
    private TextView less, add;
    private ImageView ivGoodsImage, ivReturn;

    private HintLayout mHintLayout;

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
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
                    createOrder.setEnabled(false);
                    break;
                case HANDLER_REFRESH_CART_LIST:
                    if (cartList.size() == 0) {
                        createOrder.setEnabled(false);
                        showEmpty();
                    } else {
                        List<Double> doubles = cartList.stream().map(MallUserCartVO::getGoodsPrice).collect(Collectors.toList());
                        double v = DoubleUtils.addDoubleList(doubles);
                        totalPrice.setText(v + "");
                        cartAdapter.setDatas(cartList);
                        cartAdapter.notifyDataSetChanged();
                        showComplete();
                    }
                    break;
                default:
                    createOrder.setEnabled(false);
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initView() {
        initToolBar(TOOLBAR_TITLE);
        mHintLayout = findViewById(R.id.hintLayout);
        refreshLayout = findViewById(R.id.refreshLayout);
        rvMallCart = findViewById(R.id.rv_mall_cart);
        totalPrice = findViewById(R.id.tv_total_price);
        ivGoodsImage = findViewById(R.id.iv_goods_img);
        tvGoodTitle = findViewById(R.id.tv_goods_title);
        tvGoodPrice = findViewById(R.id.tv_goods_price);
        ivReturn = findViewById(R.id.iv_return);
        btnGoodsCount = findViewById(R.id.btn_goods_count);
        less = findViewById(R.id.tv_goods_less);
        add = findViewById(R.id.tv_goods_add);
        createOrder = findViewById(R.id.btn_create_order);
        showLoading();
    }

    @Override
    protected void initData() {
        linearLayoutManager = new LinearLayoutManager(CartActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMallCart.setLayoutManager(linearLayoutManager);
        cartAdapter = new CartAdapter(this);
        rvMallCart.setAdapter(cartAdapter);
        cartAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                MallUserCartVO cartVO = cartList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("cartId", cartVO.getCartId());
                showShortToast("cartId" + cartVO.getCartId());
//                navigateToWithBundle(GoodDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                String title = "删除购物车";
                String msg = "确定删除商品：" + cartList.get(position).getGoodsTitle() + "吗？";
                AlertDialogX.showCustomAlertDialog(CartActivity.this, title, msg, new View.OnClickListener() {
                    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
                    @Override
                    public void onClick(View view) {
                        String cartId = cartList.get(position).getCartId();
                        delCart(cartId);
                        cartList.remove(position);
                        showShortToast("删除成功");
                        // 重新计算总价格
                        Double collect = cartList.stream().mapToDouble(MallUserCartVO::getGoodsPrice).sum();
                        totalPrice.setText(collect + "");
                        cartAdapter.notifyDataSetChanged();
                        // 若无商品，展示空页面
                        if (cartList.size() == 0) {
                            createOrder.setEnabled(false);
                            showEmpty();
                        }
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getCartList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                pageNum++;
                getCartList(false);
            }
        });
        getCartList(true);
        createOrder.setOnClickListener(v -> {
            List<String> goodsIds = cartList.stream().map(MallUserCartVO::getGoodsId).collect(Collectors.toList());
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("goodsIds", (ArrayList<String>) goodsIds);
            navigateToWithBundle(CreateOrderActivity.class, bundle);
        });
    }

    private void getCartList(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE);
        Api.build(ApiConfig.MALL_CART_LIST, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("getCartList", res);
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                // 后端传递时间格式解析
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                MallUserCartListRes response = gson.fromJson(res, MallUserCartListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<MallUserCartVO> cartVOList = response.getData();
                    if (cartVOList != null && cartVOList.size() > 0) {
                        if (isRefresh) {
                            cartList = cartVOList;
                        } else {
                            cartList.addAll(cartVOList);
                        }
                        mHandler.sendEmptyMessage(HANDLER_REFRESH_CART_LIST);
                    } else {
                        if (isRefresh) {
                            Log.e("getCartList", "暂时无数据");
                            mHandler.sendEmptyMessage(HANDLER_WHAT_EMPTY);
                        } else {
                            Log.e("getCartList", "没有更多数据");
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

    private void delCart(String cartId) {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.MALL_DEL_CART + cartId, params).delRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                BaseResponse response = new Gson().fromJson(res, BaseResponse.class);
                if (response != null && response.getCode() == 200) {
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

}