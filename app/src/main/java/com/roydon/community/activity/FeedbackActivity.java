package com.roydon.community.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.action.StatusAction;
import com.roydon.community.action.ToastAction;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.response.BaseResponse;
import com.roydon.community.utils.string.TelephoneUtils;
import com.roydon.community.widget.HintLayout;

import java.util.HashMap;

public class FeedbackActivity extends BaseActivity implements StatusAction, ToastAction {
    private String TOOL_TITLE = "功能反馈";

    // handler
    private static final int HANDLER_WHAT_COMPLETE = 0;

    private EditText etTelephone, etRemark;
    private Button btnFeedbackConfirm;

    private HintLayout mHintLayout;

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_WHAT_COMPLETE:
                    showLayout(R.drawable.icon_complate, R.string.feedback_success, null);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        initToolBar(TOOL_TITLE);
        mHintLayout = findViewById(R.id.hintLayout);
        etTelephone = findViewById(R.id.et_telephone);
        etRemark = findViewById(R.id.et_remark);
        btnFeedbackConfirm = findViewById(R.id.btn_feedback_confirm);
    }

    @Override
    protected void initData() {
        btnFeedbackConfirm.setOnClickListener(v -> {
            if (!TelephoneUtils.isValidPhoneNumber(etTelephone.getText().toString())) {
                toast("请输入正确的手机号");
                return;
            }
            if (etRemark.getText().toString().length() > 200) {
                toast("内容描述文本限制200字符");
                return;
            }
            addFeedback(etTelephone.getText().toString(), etRemark.getText().toString(), null);
        });
    }

    /**
     * 添加系统反馈
     */
    private void addFeedback(String telephone, String remark, String feedbackImage) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("telephone", telephone);
        params.put("remark", remark);
        params.put("feedbackImage", feedbackImage);
        params.put("feedbackPlatform", "0");
        Api.build(ApiConfig.SYS_FEEDBACK_ADD, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("addFeedback", res);
                BaseResponse response = new Gson().fromJson(res, BaseResponse.class);
                if (response != null && response.getCode() == 200) {
                    toast("上报成功");
                    mHandler.sendEmptyMessage(HANDLER_WHAT_COMPLETE);
                }
            }

            @Override
            public void onFailure(Exception e) {
                toast("上报失败");
            }
        });
    }
}