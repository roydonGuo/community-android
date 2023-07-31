package com.roydon.community.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.response.UserInfoRes;
import com.roydon.community.domain.vo.AppUser;
import com.roydon.community.enums.TenantTypeEnum;
import com.roydon.community.listener.OnPhotoSelectDialogClickListener;
import com.roydon.community.utils.img.PhotoUtils;
import com.roydon.community.utils.string.StringUtil;
import com.roydon.community.view.CircleTransform;
import com.roydon.community.view.DialogX;
import com.roydon.community.widget.RoundImageView;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class UserInfoActivity extends BaseActivity {

    // 页面传值常量
    private static final int TAKE_PHOTO = 1;
    private static final int FROM_ALBUMS = 2;

    /**
     * 顶部top-bar功能栏
     */
    private ImageView ivReturn;

    // 头像
    private LinearLayout llEditAvatar;
    private Uri avatarUri;
    private String imagePath;  //从相册中选的地址

    private RoundImageView riUserAvatar;

    private PhotoUtils photoUtils;

    private AppUser appUser;

    private TextView tvUserId, tvUserName, tvNickName, tvRealName, tvPhonenumber, tvEmail, tvIdCard, tvSex, tvAge, tvIsTenant;

    @Override
    protected int initLayout() {
        return R.layout.activity_user_info;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 2:
                    showUserInfo(appUser);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        ivReturn = findViewById(R.id.iv_return);

        llEditAvatar = findViewById(R.id.ll_edit_avatar);
        // 用户头像
        riUserAvatar = findViewById(R.id.ri_user_avatar);
        tvUserId = findViewById(R.id.tv_user_id);
        tvUserName = findViewById(R.id.tv_user_name);
        tvNickName = findViewById(R.id.tv_nick_name);
        tvRealName = findViewById(R.id.tv_real_name);
        tvPhonenumber = findViewById(R.id.tv_phonenumber);
        tvEmail = findViewById(R.id.tv_email);
        tvIdCard = findViewById(R.id.tv_id_card);
        tvSex = findViewById(R.id.tv_sex);
        tvAge = findViewById(R.id.tv_age);
        tvIsTenant = findViewById(R.id.tv_is_tenant);
    }

    @Override
    protected void initData() {
        getUserInfo();
        ivReturn.setOnClickListener(v -> {
            finish();
        });
        // 头像点击事件
        llEditAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectDialog();
            }
        });
    }

    // ui渲染=============================================================================================================
    @SuppressLint("SetTextI18n")
    private void showUserInfo(AppUser appUser) {
        if (appUser.getAvatar() != null && !appUser.getAvatar().equals("")) {
            Picasso.with(this).load(appUser.getAvatar()).transform(new CircleTransform()).into(riUserAvatar);
        }
        tvUserId.setText(appUser.getUserId() + "");
        tvUserName.setText(appUser.getUserName());
        tvNickName.setText(appUser.getNickName());
        tvRealName.setText(appUser.getRealName());
        tvPhonenumber.setText(appUser.getPhonenumber());
        tvEmail.setText(appUser.getEmail());
        tvIdCard.setText(appUser.getIdCard());
        tvSex.setText(appUser.getSex().equals("0") ? "男" : "女");
        tvAge.setText(appUser.getAge() + "");
        String isTenant = appUser.getIsTenant();
        if (isTenant.equals(TenantTypeEnum.NO.getCode())) {
            // 房东
            tvIsTenant.setText(TenantTypeEnum.NO.getInfo());
        } else if (isTenant.equals(TenantTypeEnum.YES.getCode())) {
            // 租户
            tvIsTenant.setText(TenantTypeEnum.YES.getInfo());
        } else {
            tvIsTenant.setText("未知");
        }
    }

    // ============================================================================================================


    // 页面跳转回来的回调=====================================================================================================================
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(StringUtil.isNull(data)){
//            return;
//        }
        switch (requestCode) {
            //拍照得到图片
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        //可能尚未指定intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        //返回有缩略图
                        if (data.hasExtra("data")) {
                            Bitmap bitmap = data.getParcelableExtra("data");
                            //得到bitmap后的操作
                            riUserAvatar.setImageBitmap(bitmap);
                        }
                    } else {
                        //由于指定了目标uri，存储在目标uri，intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        // 通过目标uri，找到图片
                        // 对图片的缩放处理
                        // 操作
                        Bitmap bitmap = null;
                        try {
                            bitmap = BitmapFactory.decodeStream((getContentResolver().openInputStream(avatarUri)));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        riUserAvatar.setImageBitmap(bitmap);
                    }
                }
                break;
            //从相册中选择图片
            case FROM_ALBUMS:
                if (resultCode == RESULT_OK) {
                    //判断手机版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        imagePath = photoUtils.handleImageOnKitKat(this, data);
                    } else {
                        imagePath = photoUtils.handleImageBeforeKitKat(this, data);
                    }
                }
                if (imagePath != null) {
                    //将拍摄的图片展示并更新数据库
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    riUserAvatar.setImageBitmap(bitmap);
//                    loginUser.setPortrait(photoUtils.bitmap2byte(bitmap));
                } else {
                    Log.d("onSelectAlbums", "没有找到图片");
                }
                break;
            default:
                break;

        }
    }

    // ==================================================================================================
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                // 申请到了相机权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case 2:
                // 申请到了相册权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbums();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    // 点击事件调用方法区域======================================
    // 展示修改头像选择框
    private void showSelectDialog() {
        DialogX.showPhotoSelectDialog(UserInfoActivity.this, new OnPhotoSelectDialogClickListener() {
            @Override
            public void onSelectPhoto() {
//                showShortToast("onSelectPhoto");
                // 点击拍照，动态申请权限
                if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    openCamera();
                }
            }

            @Override
            public void onSelectAlbums() {
//                showShortToast("onSelectAlbums");
                // 点击从相册选择，申请权限
                if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                } else {
                    openAlbums();
                }

            }

            @Override
            public void onSelectCancel() {
            }
        });
    }


    //=============================================

    // 打开相机拍照
    private void openCamera() {
        photoUtils = new PhotoUtils();
        avatarUri = photoUtils.take_photo_util(UserInfoActivity.this, "com.roydon.community.fileprovider", "output_image.jpg");
        //调用相机，拍摄结果会存到imageUri也就是outputImage中
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, avatarUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void openAlbums() {
        //打开相册
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, FROM_ALBUMS);
    }


    // api============================================================================

    /**
     * 用户信息
     */
    private void getUserInfo() {
        HashMap<String, Object> params = new HashMap<>();
        Api.build(ApiConfig.USER_INFO, params).getRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("getUserInfo", res);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                UserInfoRes response = gson.fromJson(res, UserInfoRes.class);
                if (response != null && response.getCode() == 200) {
                    AppUser user = response.getData();
                    if (StringUtil.isNotNull(user)) {
                        Log.e("getUserInfo", user.toString());
                        appUser = user;
                        mHandler.sendEmptyMessage(2);
                    } else {
                        showShortToast("请重新登陆");
//                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}
