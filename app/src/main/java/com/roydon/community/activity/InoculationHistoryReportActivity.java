package com.roydon.community.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.response.BaseResponse;
import com.roydon.community.domain.response.UploadImageRes;

import java.io.File;
import java.util.HashMap;

public class InoculationHistoryReportActivity extends BaseActivity {
    private String TOOLBAR_TITLE = "疫苗接种记录上报";

    private static final int PICK_IMAGE_REQUEST = 1;

    // toolbar
    private ImageView ivReturn;
    private TextView tvToolTitle;

    // 控件
    private EditText etRealName, etTelephone, etIdCard;
    private ImageView ivUploadImage;
    private Button btnInoculationHistoryReportConfirm;

    // 审核图片的url
    private String uploadedImagePath;
    private String uploadedImageUrl;

    @Override
    protected int initLayout() {
        return R.layout.activity_inoculation_history_report;
    }

    @Override
    protected void initView() {
        // toolbar
        ivReturn = findViewById(R.id.iv_return);
        tvToolTitle = findViewById(R.id.tv_tool_title);
        tvToolTitle.setText(TOOLBAR_TITLE);
        // 控件
        etRealName = findViewById(R.id.et_real_name);
        etTelephone = findViewById(R.id.et_telephone);
        etIdCard = findViewById(R.id.et_id_card);
        ivUploadImage = findViewById(R.id.iv_upload_image);
        btnInoculationHistoryReportConfirm = findViewById(R.id.btn_inoculation_history_report_confirm);

    }

    @Override
    protected void initData() {
        ivReturn.setOnClickListener(v -> {
            finish();
        });
        // 上传图片
        ivUploadImage.setOnClickListener(v -> {
            openImagePicker();
        });
        // 提交
        btnInoculationHistoryReportConfirm.setOnClickListener(v -> {
            reportAudit(etRealName.getText().toString(), etTelephone.getText().toString(), etIdCard.getText().toString(), uploadedImageUrl);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            uploadedImagePath = getRealPathFromUri(selectedImageUri);
            displaySelectedImage(uploadedImagePath);
            uploadImage(uploadedImagePath);
        }
    }

    /**
     * 选择图片
     */
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(columnIndex);
        cursor.close();
        return imagePath;
    }

    private void displaySelectedImage(String imagePath) {
        ivUploadImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
    }

    /**
     * 上传审核图片
     *
     * @param imagePath
     * @return imgUrl
     */
    private void uploadImage(String imagePath) {
        // 在这里实现图片上传的逻辑发送图片到服务器
        File file = new File(imagePath);
        Api.buildNoParams(ApiConfig.INOCULATION_AUDIT_UPLOAD_IMAGE).postImgRequestWithToken(this, "imagefile", file, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("uploadImage", res);
                UploadImageRes response = new Gson().fromJson(res, UploadImageRes.class);
                if (response != null && response.getCode() == 200) {
                    uploadedImageUrl = response.getImgUrl();
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("uploadImage", "上传出现了问题");
                e.printStackTrace();
            }
        });
    }

    /**
     * 提交疫苗接种审核
     */
    private void reportAudit(String realName, String telephone, String idCard, String auditImage) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("realName", realName);
        params.put("telephone", telephone);
        params.put("idCard", idCard);
        params.put("auditImage", auditImage);
        Api.build(ApiConfig.INOCULATION_AUDIT_REPORT, params).postRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("reportAudit", res);
                BaseResponse response = new Gson().fromJson(res, BaseResponse.class);
                if (response != null && response.getCode() == 200) {
                    showShortToast("提交成功");
                    finish();
                }
            }

            @Override
            public void onFailure(Exception e) {
                showShortToast("提交失败");
            }
        });
    }

}