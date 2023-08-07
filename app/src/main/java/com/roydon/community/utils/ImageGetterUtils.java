//package com.roydon.community.utils;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Matrix;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.text.Html;
//import android.util.Log;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.SimpleTarget;
//import com.bumptech.glide.request.transition.Transition;
//
//public class ImageGetterUtils {
//    public static MyImageGetter getImageGetter(Context context, TextView textView) {
//        MyImageGetter myImageGetter = new MyImageGetter(context, textView);
//        return myImageGetter;
//    }
//
//    public static class MyImageGetter implements Html.ImageGetter {
//
//        private URLDrawable urlDrawable = null;
//        private TextView textView;
//        private Context context;
//
//        public MyImageGetter(Context context, TextView textView) {
//            this.textView = textView;
//            this.context = context;
//        }
//
//        @Override
//        public Drawable getDrawable(final String source) {
//            urlDrawable = new URLDrawable();
//            Glide.with(context).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                    urlDrawable.bitmap = changeBitmapSize(resource);
//                    urlDrawable.setBounds(0, 0, changeBitmapSize(resource).getWidth(), changeBitmapSize(resource).getHeight());
//                    textView.invalidate();
//                    textView.setText(textView.getText());//不加这句显示不出来图片，原因不详
//                }
//            });
////
//            return urlDrawable;
//        }
//
//        public class URLDrawable extends BitmapDrawable {
//            public Bitmap bitmap;
//
//            @Override
//            public void draw(Canvas canvas) {
//                super.draw(canvas);
//                if (bitmap != null) {
//                    canvas.drawBitmap(bitmap, 0, 0, getPaint());
//                }
//            }
//        }
//
//        private Bitmap changeBitmapSize(Bitmap bitmap) {
//            int width = bitmap.getWidth();
//            int height = bitmap.getHeight();
//            Log.e("width", "width:" + width);
//
//            Log.e("height", "height:" + height);
//            //设置想要的大小
//            int newWidth = width;
//            int newHeight = height;
//            //计算压缩的比率
//            float scaleWidth = ((float) newWidth) / width;
//            float scaleHeight = ((float) newHeight) / height;
//            //获取想要缩放的matrix
//            Matrix matrix = new Matrix();
//            matrix.postScale(scaleWidth, scaleHeight);
//            //获取新的bitmap
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
//
//            bitmap.getWidth();
//
//            bitmap.getHeight();
//
//            Log.e("newWidth", "newWidth" + bitmap.getWidth());
//
//            Log.e("newHeight", "newHeight" + bitmap.getHeight());
//
//            return bitmap;
//
//        }
//
//
//    }
//}