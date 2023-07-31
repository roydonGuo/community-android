package com.roydon.community.api;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.roydon.community.activity.LoginActivity;
import com.roydon.community.constants.Constants;
import com.roydon.community.constants.HttpStatus;
import com.roydon.community.utils.string.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {
    private static OkHttpClient client;
    private static String requestUrl;
    private static HashMap<String, Object> mParams;
    public static Api api = new Api();

    public Api() {
    }

    public static Api buildNoParams(String url) {
        client = new OkHttpClient.Builder().build();
        requestUrl = ApiConfig.BASE_URl + url;
        return api;
    }

    public static Api build(String url, HashMap<String, Object> params) {
        client = new OkHttpClient.Builder().build();
        requestUrl = ApiConfig.BASE_URl + url;
        mParams = params;
        return api;
    }

    /**
     * post请求
     *
     * @param context
     * @param callback
     */
    public void postRequest(Context context, final HttpCallback callback) {
        JSONObject jsonObject = new JSONObject(mParams);
        String jsonStr = jsonObject.toString();
        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonStr);
        //第三步创建Request
        Request request = new Request.Builder().url(requestUrl).addHeader("contentType", "application/json;charset=UTF-8").post(requestBodyJson).build();
        //第四步创建call回调对象
        final Call call = client.newCall(request);
        //第五步发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (StringUtil.isNotNull(response.body())) {
                    final String result = response.body().string();
                    callback.onSuccess(result);
                }
            }
        });
    }

    /**
     * post请求with token
     *
     * @param context
     * @param callback
     */
    public void postRequestWithToken(Context context, final HttpCallback callback) {
        SharedPreferences sp = context.getSharedPreferences("sp_roydon", MODE_PRIVATE);
        String token = Constants.TOKEN_PREFIX + sp.getString(Constants.TOKEN, "");
        JSONObject jsonObject = new JSONObject(mParams);
        String jsonStr = jsonObject.toString();
        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonStr);
        //第三步创建Request
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("contentType", "application/json;charset=UTF-8")
                .addHeader(Constants.AUTHORIZATION, token)
                .post(requestBodyJson).build();
        //第四步创建call回调对象
        final Call call = client.newCall(request);
        //第五步发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (StringUtil.isNotNull(response.body())) {
                    final String result = response.body().string();
                    Log.e("onSuccess", result);
                    callback.onSuccess(result);
                }
            }
        });
    }

    /**
     * 上传图片post请求with token
     *
     * @param context
     * @param callback
     */
    public void postImgRequestWithToken(Context context, File file, final HttpCallback callback) {
        SharedPreferences sp = context.getSharedPreferences("sp_roydon", MODE_PRIVATE);
        String token = Constants.TOKEN_PREFIX + sp.getString(Constants.TOKEN, "");
//        JSONObject jsonObject = new JSONObject(mParams);
//        String jsonStr = jsonObject.toString();
//        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("image/*;charset=utf-8"), jsonStr);
        //所有图片类型
        MediaType mediaType = MediaType.parse("multipart/form-data");
        //第一步，说明数据为文件，以及文件类型
        RequestBody fileBody = RequestBody.create(mediaType, file);
        //第二步，指明服务表单的键名，文件名，文件体
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("avatarfile", file.getName(), fileBody)
                .build();
        //第三步创建Request
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader(Constants.AUTHORIZATION, token)
                .post(requestBody).build();
        //第四步创建call回调对象
        final Call call = client.newCall(request);
        //第五步发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (StringUtil.isNotNull(response.body())) {
                    final String result = response.body().string();
                    Log.e("onSuccess", result);
                    callback.onSuccess(result);
                }
            }
        });
    }

    /**
     * get请求
     *
     * @param context
     * @param callback
     */
    public void getRequest(Context context, final HttpCallback callback) {
        String url = getAppendUrl(requestUrl, mParams);
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (StringUtil.isNotNull(response.body())) {
                    final String result = response.body().string();
                    Log.e("onSuccess", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = (int) jsonObject.get("code");
                        if (code == HttpStatus.UNAUTHORIZED) {
                            Intent in = new Intent(context, LoginActivity.class);
                            context.startActivity(in);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callback.onSuccess(result);
                }
            }
        });
    }

    /**
     * get请求WithToken
     *
     * @param context
     * @param callback
     */
    public void getRequestWithToken(Context context, final HttpCallback callback) {
        SharedPreferences sp = context.getSharedPreferences("sp_roydon", MODE_PRIVATE);
        String token = Constants.TOKEN_PREFIX + sp.getString(Constants.TOKEN, "");
        String url = getAppendUrl(requestUrl, mParams);
        Request request = new Request.Builder()
                .url(url)
                .addHeader(Constants.AUTHORIZATION, token)
                .get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (StringUtil.isNotNull(response.body())) {
                    final String result = response.body().string();
                    Log.e("onSuccess", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = (int) jsonObject.get("code");
                        if (code == HttpStatus.UNAUTHORIZED) {
                            Intent in = new Intent(context, LoginActivity.class);
                            context.startActivity(in);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callback.onSuccess(result);
                }
            }
        });
    }

    /**
     * del请求with token
     *
     * @param context
     * @param callback
     */
    public void delRequestWithToken(Context context, final HttpCallback callback) {
        SharedPreferences sp = context.getSharedPreferences("sp_roydon", MODE_PRIVATE);
        String token = Constants.TOKEN_PREFIX + sp.getString(Constants.TOKEN, "");
        JSONObject jsonObject = new JSONObject(mParams);
        String jsonStr = jsonObject.toString();
        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonStr);
        //第三步创建Request
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("contentType", "application/json;charset=UTF-8")
                .addHeader(Constants.AUTHORIZATION, token)
                .delete(requestBodyJson).build();
        //第四步创建call回调对象
        final Call call = client.newCall(request);
        //第五步发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (StringUtil.isNotNull(response.body())) {
                    final String result = response.body().string();
                    Log.e("onSuccess", result);
                    callback.onSuccess(result);
                }
            }
        });
    }

    private String getAppendUrl(String url, Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                if (StringUtil.isEmpty(buffer.toString())) {
                    buffer.append("?");
                } else {
                    buffer.append("&");
                }
                buffer.append(entry.getKey()).append("=").append(entry.getValue());
            }
            url += buffer.toString();
        }
        return url;
    }

}
