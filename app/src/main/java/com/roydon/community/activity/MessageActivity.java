package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.action.StatusAction;
import com.roydon.community.adapter.ChatListAdapter;
import com.roydon.community.adapter.LocalDateTimeTypeAdapter;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.entity.ChatList;
import com.roydon.community.domain.response.ChatListListRes;
import com.roydon.community.listener.OnItemClickListener;
import com.roydon.community.ui.popup.MenuPopup;
import com.roydon.community.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends BaseActivity implements StatusAction {

    private TitleBar mTitleBar;

    // handler
    private static final int HANDLER_WHAT_EMPTY = 0;
    private static final int HANDLER_WHAT_CHAT_LIST = 1;

    private HintLayout mHintLayout;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private ChatListAdapter chatListAdapter;
    private int pageNum = 1;
    private List<ChatList> chatLists;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(new Handler.Callback() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_WHAT_EMPTY:
                    showEmpty();
                    break;
                case HANDLER_WHAT_CHAT_LIST:
                    chatListAdapter.setList(chatLists);
                    chatListAdapter.notifyDataSetChanged();
                    showComplete();
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        mHintLayout = findViewById(R.id.hintLayout);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        showLoading();
        // 默认状态栏字体颜色为黑色
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        // 设置标题栏沉浸
        if (mTitleBar != null) {
            ImmersionBar.setTitleBar(this, mTitleBar);
        }
    }

    @Override
    protected void initData() {
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }

            @Override
            public void onTitleClick(TitleBar titleBar) {
                OnTitleBarListener.super.onTitleClick(titleBar);
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                OnTitleBarListener.super.onRightClick(titleBar);
                // 菜单弹窗
                new MenuPopup.Builder(MessageActivity.this).setList("添加好友").setListener((MenuPopup.OnListener<String>) (popupWindow, position, s) -> toast(s)).showAsDropDown(titleBar.getRightView());

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatListAdapter = new ChatListAdapter(this);
        recyclerView.setAdapter(chatListAdapter);
        chatListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("receiver", chatLists.get(position).getReceiver());
                bundle.putString("receiverAvatar", chatLists.get(position).getReceiverImage());
                navigateToWithBundle(ChatPrivateActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                toast("长按了");
            }
        });
        getChatListNormal(true);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            getChatListNormal(true);
        });
        refreshLayout.setOnLoadMoreListener((refreshlayout) -> {
            pageNum++;
            getChatListNormal(false);
        });

    }

    /**
     * 获取聊天列表
     *
     * @param isRefresh
     */
    private void getChatListNormal(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE);
        Api.build(ApiConfig.CHAT_LIST_NORMAL, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).create();
                ChatListListRes response = gson.fromJson(res, ChatListListRes.class);
                if (response != null && response.getCode() == 200) {
                    List<ChatList> list = response.getData();
                    if (list != null && list.size() > 0) {
                        if (isRefresh) {
                            chatLists = list;
                        } else {
                            chatLists.addAll(list);
                        }
                        mHandler.sendEmptyMessage(HANDLER_WHAT_CHAT_LIST);
                    } else {
                        if (isRefresh) {
                            mHandler.sendEmptyMessage(HANDLER_WHAT_EMPTY);
                            Log.e("getChatListNormal", "暂时无数据");
                        } else {
                            Log.e("getChatListNormal", "没有更多数据");
                        }
                    }
                } else {
                    if (response == null) {
                        Log.e("getChatListNormal", "响应为空");
                    } else {
                        toast(response.getCode());
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                toast("请求失败！");
            }
        });
    }


}