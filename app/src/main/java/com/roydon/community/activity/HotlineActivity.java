package com.roydon.community.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.adapter.HotlineAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.AppHotline;
import com.roydon.community.domain.response.AppHotlineListRes;
import com.roydon.community.enums.NormalDisableEnum;
import com.roydon.community.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author roydon
 * @description 紧急热线
 */
public class HotlineActivity extends BaseActivity {

    public static final int REQUEST_CALL_PERMISSION = 10111; //拨号请求码

    /**
     * 顶部top-bar功能栏
     */
    private ImageView ivReturn;

    /**
     * 进度条
     */
    private ProgressBar loadingSpinner;

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    // 数据
    private int pageNum = 1;
    private List<AppHotline> hotlineList = new ArrayList<>();

    // 适配器
    private HotlineAdapter hotlineAdapter;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    hotlineAdapter.setData(hotlineList);
                    hotlineAdapter.notifyDataSetChanged();
                    // 禁用进度条
                    loadingSpinner.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_hotline;
    }

    @Override
    protected void initView() {
        loadingSpinner = findViewById(R.id.loading_spinner);
        // 加载进度条
        loadingSpinner.setVisibility(View.VISIBLE);
        ivReturn = findViewById(R.id.iv_return);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });

        // 设置适配器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        hotlineAdapter = new HotlineAdapter(this);
        recyclerView.setAdapter(hotlineAdapter);
        hotlineAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                call(hotlineList.get(position).getTelephone());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        getHotlineList(true);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getHotlineList(true);
        });
        refreshLayout.setOnLoadMoreListener((refreshlayout) -> {
            pageNum++;
            getHotlineList(false);
        });

    }

    /**
     * 判断是否有某项权限
     *
     * @param string_permission 权限
     * @param request_code      请求码
     * @return
     */
    public boolean checkReadPermission(String string_permission, int request_code) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(this, string_permission) == PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(this, new String[]{string_permission}, request_code);
        }
        return flag;
    }

    /**
     * 检查权限后的回调
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: //拨打电话
                if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                    Toast.makeText(this, "请允许拨号权限后再试", Toast.LENGTH_SHORT).show();
                } else {
                    //成功
                    call("13592342233");
                }
                break;
        }
    }
    /**
     * 1.直接拨打电话。
     *
     *  Intent Intent =  new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phoneNumber));
     *  startActivity(Intent);
     *
     * 2.Android跳转到拨号界面。
     *
     *  Intent Intent =  new Intent(Intent.ACTION_CALL_BUTTON);//跳转到拨号界面
     *  startActivity(Intent);
     *
     * 3.Android跳转到拨号界面，同时传递电话号码。
     *
     *  Intent Intent =  new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + phoneNumber));
     *  startActivity(Intent);
     */

    /**
     * 拨打电话（跳转到拨号界面）
     *
     * @param telPhone 电话
     */
    public void call(String telPhone) {
        if (checkReadPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PERMISSION)) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telPhone));
            startActivity(intent);
        }
    }

    private void getHotlineList(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", 10);
        params.put("status", NormalDisableEnum.OK.getCode());
        Api.build(ApiConfig.APP_HOTLINE_ALL, params).getRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                AppHotlineListRes response = gson.fromJson(res, AppHotlineListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<AppHotline> list = response.getRows();
                    if (list != null && list.size() > 0) {
                        if (isRefresh) {
                            hotlineList = list;
                        } else {
                            hotlineList.addAll(list);
                        }
                        mHandler.sendEmptyMessage(0);
                    } else {
                        if (isRefresh) {
                            Log.e("getHotlineList", "暂时无数据");
                        } else {
                            Log.e("getHotlineList", "没有更多数据");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}