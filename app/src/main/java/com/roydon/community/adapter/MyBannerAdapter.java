//package com.roydon.community.adapter;
//
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
//import com.bumptech.glide.request.RequestOptions;
//import com.youth.banner.adapter.BannerAdapter;
//
//import java.util.List;
//
//public class MyBannerAdapter extends BannerAdapter<String, MyBannerAdapter.BannerViewHolder> {
//
//    public MyBannerAdapter(List<String> datas) {
//        super(datas);
//    }
//
//    @Override
//    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
//        ImageView imageView = new ImageView(parent.getContext());
//        //注意，必须设置为match_parent，这个是viewpager2强制要求的
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        imageView.setLayoutParams(params);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        return new BannerViewHolder(imageView);
//    }
//
//    @Override
//    public void onBindView(BannerViewHolder holder, String data, int position, int size) {
//        RoundedCorners roundedCorners = new RoundedCorners(10);
//        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
//        Glide.with(holder.itemView).load(data).apply(options).into(holder.imageView);
////        holder.imageView.setImageResource(data.getOguyEkaJ());
//        //设置点击事件
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("tttttttt", data);
//            }
//        });
//
//    }
//
//    class BannerViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//
//        public BannerViewHolder(@NonNull ImageView view) {
//            super(view);
//            this.imageView = view;
//        }
//    }
//
//}