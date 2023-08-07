package com.roydon.community.activity;

import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.constants.CacheConstants;
import com.roydon.community.utils.cache.SPUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Date;

public class MessageActivity extends BaseActivity {

    private TextView showMessage;
    private EditText editText;
    private Button btnSend;

    private WebSocketClient webSocketClient;
    private StringBuilder sb = new StringBuilder();

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            sb.append("服务器返回数据：");
            sb.append(msg.obj.toString());
            sb.append("\n");
            showMessage.setText(sb.toString());
            return true;
        }
    });

    @Override
    protected int initLayout() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        showMessage = findViewById(R.id.show_message);
        editText = findViewById(R.id.edit_text);
        btnSend = findViewById(R.id.send);
    }

    @Override
    protected void initData() {
        URI serverURI = URI.create("ws://106.14.105.101:8088/chat-server/" + SPUtils.getString(CacheConstants.USERNAME, "", this));
        webSocketClient = new WebSocketClient(serverURI) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                sb.append("onOpen at time：");
                sb.append(new Date());
                sb.append("服务器状态：");
                sb.append(handshakedata.getHttpStatusMessage());
                sb.append("\n");
                showMessage.setText(sb.toString());
            }

            @Override
            public void onMessage(String message) {
                Message handlerMessage = Message.obtain();
                handlerMessage.obj = message;
                handler.sendMessage(handlerMessage);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                sb.append("onClose at time：");
                sb.append(new Date());
                sb.append("\n");
                sb.append("onClose info:");
                sb.append(code);
                sb.append(reason);
                sb.append(remote);
                sb.append("\n");
                showMessage.setText(sb.toString());
            }

            @Override
            public void onError(Exception ex) {
                sb.append("onError at time：");
                sb.append(new Date());
                sb.append("\n");
                sb.append(ex);
                sb.append("\n");
                showMessage.setText(sb.toString());
            }
        };
        webSocketClient.connect();


        btnSend.setOnClickListener(v -> {
            if (webSocketClient.isClosed() || webSocketClient.isClosing()) {
//                Snackbar.make(v, "Client正在关闭", Snackbar.LENGTH_SHORT).show();
                webSocketClient.connect();
                return;
            }
            webSocketClient.send(editText.getText().toString().trim());
            sb.append("客户端发送消息：");
            sb.append(new Date());
            sb.append("\n");
            sb.append(editText.getText().toString().trim());
            sb.append("\n");
            showMessage.setText(sb.toString());
            editText.setText("");
        });
    }


}