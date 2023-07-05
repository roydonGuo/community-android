package com.roydon.community.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadImageTask extends AsyncTask<Object, Void, Bitmap> {

    private ImageView imageView;

    @Override
    protected Bitmap doInBackground(Object... params) {
        String imageUrl = (String) params[0];
        imageView = (ImageView) params[1];

        try {
            // 使用URL加载图片
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            // 将输入流转换为Bitmap对象
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            inputStream.close();
            connection.disconnect();

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            // 在ImageView中显示加载的图片
            imageView.setImageBitmap(result);
        }
    }
}
