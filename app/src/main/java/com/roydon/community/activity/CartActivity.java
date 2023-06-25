package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.adapter.CartAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.MallUserCartVO;
import com.roydon.community.domain.vo.BaseResponse;
import com.roydon.community.domain.vo.MallUserCartListRes;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartActivity extends BaseActivity {

    private RefreshLayout refreshLayout;
    private RecyclerView rvMallCart;
    private LinearLayoutManager linearLayoutManager;
    private int pageNum = 1;
    private CartAdapter cartAdapter;

    private List<MallUserCartVO> cartList = new ArrayList<>();

    private TextView tvGoodTitle, tvGoodPrice, totalPrice;
    private Button btnGoodsCount;
    private TextView less, add;
    private ImageView ivGoodsImage, ivReturn;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Double collect = cartList.stream().mapToDouble(MallUserCartVO::getGoodsPrice).sum();
                    totalPrice.setText("￥" + collect);
                    cartAdapter.setDatas(cartList);
                    cartAdapter.notifyDataSetChanged();
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
    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
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
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("⛔删除")
                        .setMessage("确定删除商品：" + cartList.get(position).getGoodsTitle() + "吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String cartId = cartList.get(position).getCartId();
                                delCart(cartId);
                                cartList.remove(position);
                                showShortToast("删除成功");
                                Double collect = cartList.stream().mapToDouble(MallUserCartVO::getGoodsPrice).sum();
                                totalPrice.setText("￥" + collect);
                                cartAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNeutralButton("取消", null)
                        .create().show();
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
                        mHandler.sendEmptyMessage(0);
                    } else {
                        if (isRefresh) {
                            Log.e("getCartList", "暂时无数据");
                            showSyncShortToast("暂时无数据");
                        } else {
                            Log.e("getCartList", "没有更多数据");
                            showSyncShortToast("没有更多数据");
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