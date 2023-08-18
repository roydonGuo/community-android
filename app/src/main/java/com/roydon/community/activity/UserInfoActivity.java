package com.roydon.community.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roydon.community.BaseActivity;
import com.roydon.community.R;
import com.roydon.community.api.Api;
import com.roydon.community.api.ApiConfig;
import com.roydon.community.api.HttpCallback;
import com.roydon.community.domain.response.BaseResponse;
import com.roydon.community.domain.response.UploadAvatarRes;
import com.roydon.community.domain.response.UserInfoRes;
import com.roydon.community.domain.vo.AppUser;
import com.roydon.community.enums.TenantTypeEnum;
import com.roydon.community.listener.OnConfirmDialogClickListener;
import com.roydon.community.listener.OnPhotoSelectDialogClickListener;
import com.roydon.community.utils.img.PhotoUtils;
import com.roydon.community.utils.string.EmailUtils;
import com.roydon.community.utils.string.StringUtil;
import com.roydon.community.utils.string.TelephoneUtils;
import com.roydon.community.view.CircleTransform;
import com.roydon.community.view.DialogX;
import com.roydon.community.widget.RoundImageView;
import com.roydon.library.layout.SettingBar;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class UserInfoActivity extends BaseActivity {
    private String TOOL_TITLE = "我的资料";

    // 页面传值常量
    private static final int TAKE_PHOTO = 100;
    private static final int FROM_ALBUMS = 200;

    // handler
    private static final int HANDLER_WHAT_USERINFO = 0;
    private static final int HANDLER_REFRESH_USERINFO = 1;

    // 头像
    private LinearLayout llEditAvatar;
    private Uri avatarUri;
    //从相册中选的地址

    private RoundImageView riUserAvatar;

    private PhotoUtils photoUtils = new PhotoUtils();

    private AppUser appUser;

    private SettingBar tvUserName,sbNickName;

    private TextView  tvRealName, tvPhonenumber, tvEmail, tvIdCard, tvSex, tvAge, tvIsTenant;

    private String mCurrentPhotoPath;

    // 编辑组件
    private LinearLayout  llEditRealName, llEditPhonenumber, llEditEmail;

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
                case HANDLER_WHAT_USERINFO:
                    showUserInfo(appUser);
                    break;
                case HANDLER_REFRESH_USERINFO:
                    getUserInfo();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        initToolBar(TOOL_TITLE);
        llEditAvatar = findViewById(R.id.ll_edit_avatar);
        // 用户头像
        riUserAvatar = findViewById(R.id.ri_user_avatar);
        tvUserName = findViewById(R.id.tv_user_name);
        sbNickName = findViewById(R.id.sb_nick_name);
        tvRealName = findViewById(R.id.tv_real_name);
        tvPhonenumber = findViewById(R.id.tv_phonenumber);
        tvEmail = findViewById(R.id.tv_email);
        tvIdCard = findViewById(R.id.tv_id_card);
        tvSex = findViewById(R.id.tv_sex);
        tvAge = findViewById(R.id.tv_age);
        tvIsTenant = findViewById(R.id.tv_is_tenant);

        // 编辑组件
//        llEditNickName = findViewById(R.id.ll_edit_nick_name);
        llEditRealName = findViewById(R.id.ll_edit_real_name);
        llEditPhonenumber = findViewById(R.id.ll_edit_phonenumber);
        llEditEmail = findViewById(R.id.ll_edit_email);
    }

    @Override
    protected void initData() {
        getUserInfo();
        // 头像点击事件
        riUserAvatar.setOnClickListener(v -> {
            showSelectDialog();
        });
        // 昵称点击编辑
        sbNickName.setOnClickListener(v -> {
            String dialogTitle = "编辑昵称";
            DialogX.showEditTextDialog(this, dialogTitle, appUser.getNickName(), new OnConfirmDialogClickListener() {
                @Override
                public void onConfirm(String result) {
                    if (result == null) {
                        showShortToast("不能为空");
                    }
                    editUserInfo("nickName", result.trim());
                }

                @Override
                public void onCancel() {

                }
            });
        });
        // 编辑姓名
        llEditRealName.setOnClickListener(v -> {
            String dialogTitle = "编辑姓名";
            DialogX.showEditTextDialog(this, dialogTitle, appUser.getRealName(), new OnConfirmDialogClickListener() {
                @Override
                public void onConfirm(String result) {
                    if (result == null) {
                        showShortToast("不能为空");
                    }
                    editUserInfo("realName", result.trim());
                }

                @Override
                public void onCancel() {

                }
            });
        });
        // 编辑手机
        llEditPhonenumber.setOnClickListener(v -> {
            String dialogTitle = "编辑手机号码";
            DialogX.showEditTextDialog(this, dialogTitle, appUser.getPhonenumber(), new OnConfirmDialogClickListener() {
                @Override
                public void onConfirm(String result) {
                    if (result == null) {
                        showShortToast("手机号不能为空");
                    } else if (!TelephoneUtils.isValidPhoneNumber(result)) {
                        showShortToast("请输入正确的手机号");
                    } else {
                        editUserInfo("phonenumber", result.trim());
                    }

                }

                @Override
                public void onCancel() {

                }
            });
        });
        llEditEmail.setOnClickListener(v -> {
            String dialogTitle = "编辑邮箱";
            DialogX.showEditTextDialog(this, dialogTitle, appUser.getEmail(), new OnConfirmDialogClickListener() {
                @Override
                public void onConfirm(String result) {
                    if (result == null) {
                        showShortToast("邮箱不能为空");
                    } else if (!EmailUtils.isValidEmail(result)) {
                        showShortToast("请输入正确邮箱");
                    } else {
                        editUserInfo("email", result.trim());
                    }
                }

                @Override
                public void onCancel() {

                }
            });
        });
    }

    // ui渲染=============================================================================================================
    @SuppressLint("SetTextI18n")
    private void showUserInfo(AppUser appUser) {
        if (appUser.getAvatar() != null && !appUser.getAvatar().equals("")) {
            // 禁止Picasso缓存用户头像
            Picasso.with(this).load(appUser.getAvatar())
                    .transform(new CircleTransform())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(riUserAvatar);
        }
        tvUserName.setRightText(appUser.getUserName() + " : " + appUser.getUserId());
        sbNickName.setRightText(appUser.getNickName());
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
                        riUserAvatar.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
                        // 发送到服务器
                        try {
                            uploadAvatar(new File(mCurrentPhotoPath));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            //从相册中选择图片
            case FROM_ALBUMS:
                if (resultCode == RESULT_OK) {
                    if (data == null) return;
                    // 判断手机版本号
                    String imagePath;
                    if (Build.VERSION.SDK_INT >= 19) {
                        imagePath = photoUtils.handleImageOnKitKat(this, data);
                    } else {
                        imagePath = photoUtils.handleImageBeforeKitKat(this, data);
                    }
                    Log.d("imagePath", imagePath);
                    if (imagePath != null) {
                        // 将拍摄的图片展示
                        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                        riUserAvatar.setImageBitmap(bitmap);
                        // 发送到服务器
                        uploadAvatar(new File(imagePath));
                    } else {
                        Log.d("onSelectAlbums", "没有找到图片");
                    }
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
                    openSysCamera();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case 2:
                // 申请到了相册权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openSysAlbums();
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
                    openSysCamera();
                }
            }

            @Override
            public void onSelectAlbums() {
//                showShortToast("onSelectAlbums");
                // 点击从相册选择，申请权限
                if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                } else {
                    openSysAlbums();
                }

            }

            @Override
            public void onSelectCancel() {
            }
        });
    }

    //=============================================

    // 打开相机拍照
    private void openSysCamera() {
//        avatarUri = photoUtils.takePhoto(UserInfoActivity.this, "com.roydon.community.fileprovider");
//        //调用相机，拍摄结果会存到imageUri也就是outputImage中
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, avatarUri);
//        startActivityForResult(intent, TAKE_PHOTO);
        // 创建照片存储目录
        File imgDir = new File(getFilePath(null));
        // 创建照片
        File picture = new File(imgDir, System.currentTimeMillis() + ".jpg");
        if (!picture.exists()) {
            try {
                picture.createNewFile();
            } catch (IOException e) {
                Log.e("createNewFile", "创建图片失败", e);
            }
        }
        mCurrentPhotoPath = picture.getAbsolutePath();
        Log.e("mCurrentPhotoPath", mCurrentPhotoPath);
        // 调用相机拍照
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, "com.roydon.community.fileprovider", picture));
        startActivityForResult(camera, TAKE_PHOTO);
    }

    private void openSysAlbums() {
        //打开相册
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //intent.setAction(Intent.ACTION_GET_CONTENT)  //实现相册多选 该方法获得的uri在转化为真实文件路径时Android 4.4以上版本会有问题
//        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, FROM_ALBUMS);
    }

    private File createPhotoFile() throws IOException {
        // 创建一个唯一的文件名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = timeStamp + "_";

        // 获取保存照片的目录创建文件
        File imageFile = File.createTempFile(imageFileName, ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        Log.e("imageFile", imageFile.getPath());
        // 返回文件路径
        return imageFile;
    }

    /**
     * 获取存储文件路径
     *
     * @param dir 选择目录
     * @return 路径
     */
    public String getFilePath(String dir) {
        String path;
        // 判断是否有外部存储，是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            path = getExternalFilesDir(dir).getAbsolutePath();
        } else {
            // 使用内部储存
            path = getFilesDir() + File.separator + dir;
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
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
                        mHandler.sendEmptyMessage(HANDLER_WHAT_USERINFO);
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

    /**
     * 上传头像
     */
    private void uploadAvatar(File file) {
        Api.buildNoParams(ApiConfig.USER_PROFILE_AVATAR).postImgRequestWithToken(this, "avatarfile", file, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("uploadAvatar", res);
                UploadAvatarRes response = new Gson().fromJson(res, UploadAvatarRes.class);
                if (response != null && response.getCode() == 200) {
                    appUser.setAvatar(response.getImgUrl());
                    if (StringUtil.isNotNull(appUser)) {
                        mHandler.sendEmptyMessage(HANDLER_WHAT_USERINFO);
                    } else {
                        showShortToast("请重新登陆");
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("uploadAvatar", "上传出现了问题");
                e.printStackTrace();
            }
        });
    }

    private void editUserInfo(String field, String value) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(field, value);
        Api.build(ApiConfig.USER_EDIT_INFO, params).putRequestWithToken(this, new HttpCallback() {
            @Override
            public void onSuccess(final String res) {
                BaseResponse response = new Gson().fromJson(res, BaseResponse.class);
                if (response != null && response.getCode() == 200) {
                    Log.e("editUserInfo", response.getMsg());
                    mHandler.sendEmptyMessage(HANDLER_REFRESH_USERINFO);
                    showSyncShortToast("更新成功");
                } else {
                    showSyncShortToast(response.getMsg());
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

}
