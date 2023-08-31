package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.adapter.ChatMessageAdapter;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.chat.domain.ChatMessageDTO;
import com.roydon.community.chat.domain.ChatUser;
import com.roydon.community.chat.domain.ChatUserList;
import com.roydon.community.constants.CacheConstants;
import com.roydon.community.listener.OnItemClickListener;
import com.roydon.community.ui.popup.MenuPopup;
import com.roydon.community.utils.cache.SPUtils;
import com.roydon.community.utils.string.DateUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ChatPrivateActivity extends BaseActivity {

    private TitleBar mTitleBar;

    // handler
    private static final int HANDLER_WHAT_ = 0;
    private static final int HANDLER_WHAT_CHAT_MESSAGE = 1;

    private EditText editText;
    private Button btnSend;

    private WebSocketClient webSocketClient;
    private StringBuilder sb = new StringBuilder();
    private ChatUserList chatUserList;
    private String sender;
    private String senderAvatar;
    private String receiver;
    private String receiverAvatar;

    private RecyclerView recyclerView;
    private ChatMessageAdapter chatMessageAdapter;

    private List<ChatMessageDTO> chatMessageDTOList = new ArrayList<>();

    private Handler handler = new Handler(new Handler.Callback() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_WHAT_CHAT_MESSAGE:
                    String message = msg.obj.toString();
//                    Object parseMessage = JSON.parse(message);
                    if (message.contains("users")) {
                        // 返回所有用户
                        JSONObject chatUserListJsonObject = JSON.parseObject(message);
                        List<ChatUser> users = JSON.parseArray(chatUserListJsonObject.getString("users"), ChatUser.class);
                        Log.e("ChatPrivateActivity", "当前在线用户" + message + "\n" + chatUserListJsonObject.toString());
                        if (!users.contains(new ChatUser(receiver))) {
                            // 接收者离线
                            mTitleBar.setTitle(receiver + "(离线)");
                        } else {
                            mTitleBar.setTitle(receiver);
                        }
                    } else {
                        ChatMessageDTO chatMessageDTO = JSON.parseObject(message, ChatMessageDTO.class);
                        chatMessageDTO.setSenderAvatar(senderAvatar);
                        chatMessageDTO.setReceiverAvatar(receiverAvatar);
                        Log.e("ChatPrivateActivity", JSON.toJSONString(chatMessageDTO));
                        chatMessageDTOList.add(chatMessageDTO);
                        chatMessageAdapter.setList(chatMessageDTOList);
                        chatMessageAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    protected int initLayout() {
        return R.layout.activity_chat_private;
    }

    @Override
    protected void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.edit_text);
        btnSend = findViewById(R.id.send);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void initData() {
        receiver = Objects.requireNonNull(getBundle()).getString("receiver");
        receiverAvatar = Objects.requireNonNull(getBundle()).getString("receiverAvatar");
        mTitleBar.setTitle(receiver);
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }

            @Override
            public void onTitleClick(TitleBar titleBar) {
                OnTitleBarListener.super.onTitleClick(titleBar);
                titleBar.setTitle(receiver);
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                OnTitleBarListener.super.onRightClick(titleBar);
                // 菜单弹窗
                new MenuPopup.Builder(ChatPrivateActivity.this)
                        .setList("添加好友")
                        .setListener((MenuPopup.OnListener<String>) (popupWindow, position, s) -> toast(s))
                        .showAsDropDown(titleBar.getRightView());

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatMessageAdapter = new ChatMessageAdapter(this);
        recyclerView.setAdapter(chatMessageAdapter);
        chatMessageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        sender = SPUtils.getString(CacheConstants.USERNAME, "", this);
        senderAvatar = SPUtils.getString(CacheConstants.USER_AVATAR, "", this);
//        toast(sender);
        URI chatServerURI = URI.create(ApiConfig.WS_URl + "/chat-server/" + sender);
        webSocketClient = new WebSocketClient(chatServerURI) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.e("ChatPrivateActivity", "onOpen===" + new Date() + serverHandshake.getHttpStatusMessage());
            }

            @Override
            public void onMessage(String message) {
                Message handlerMessage = Message.obtain();
                handlerMessage.what = HANDLER_WHAT_CHAT_MESSAGE;
                handlerMessage.obj = message;
                handler.sendMessage(handlerMessage);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.e("ChatPrivateActivity", "onClose===" + code + reason + remote);
            }

            @Override
            public void onError(Exception ex) {
                Log.e("ChatPrivateActivity", "onError===" + ex);
            }
        };
        webSocketClient.connect();
        btnSend.setOnClickListener(v -> {
            if (webSocketClient.isClosed() || webSocketClient.isClosing()) {
                webSocketClient.connect();
                return;
            }
            ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
            chatMessageDTO.setSender(sender);
            chatMessageDTO.setSenderAvatar(senderAvatar);
            chatMessageDTO.setReceiver(receiver);
            chatMessageDTO.setReceiverAvatar(receiverAvatar);
            chatMessageDTO.setSendType(true);
            chatMessageDTO.setContent(editText.getText().toString().trim());
            chatMessageDTO.setType("0");
            chatMessageDTO.setTime(DateUtils.localDateTime2Long(LocalDateTime.now()));
            chatMessageDTOList.add(chatMessageDTO);
            chatMessageAdapter.setList(chatMessageDTOList);
            chatMessageAdapter.notifyDataSetChanged();
            webSocketClient.send(JSON.toJSONString(chatMessageDTO));
            editText.setText("");
        });
    }

    @Override
    protected void onDestroy() {
        webSocketClient.close();
        super.onDestroy();
    }
}