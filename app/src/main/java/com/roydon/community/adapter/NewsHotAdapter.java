package com.roydon.community.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roydon.community.R;
import com.roydon.community.domain.vo.HotNews;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class NewsHotAdapter extends RecyclerView.Adapter<NewsHotAdapter.NewsHotHolder> {

    private final Context mContext;
    private List<HotNews> data;

    public void setData(List<HotNews> data) {
        this.data = data;
    }

    public NewsHotAdapter(Context context) {
        this.mContext = context;
    }

    public NewsHotAdapter(Context context, List<HotNews> data) {
        this.mContext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public NewsHotAdapter.NewsHotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_hot, parent, false);
        return new NewsHotAdapter.NewsHotHolder(view);
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull NewsHotAdapter.NewsHotHolder holder, int position) {
        HotNews hotNews = data.get(position);
        Picasso.with(mContext).load(hotNews.getCoverImg()).into(holder.coverImg);
        holder.newsTitle.setText(hotNews.getNewsTitle());
        holder.source.setText(hotNews.getSource());
        holder.viewNum.setText(hotNews.getViewNum() + "");
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        holder.postTime.setText(sdf.format(hotNews.getPostTime()));
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public class NewsHotHolder extends RecyclerView.ViewHolder {
        private final ImageView coverImg;
        private final TextView newsTitle, source, viewNum, postTime;

        public NewsHotHolder(@NonNull View view) {
            super(view);
            coverImg = view.findViewById(R.id.tv_cover_img);
            newsTitle = view.findViewById(R.id.tv_news_title);
            source = view.findViewById(R.id.tv_source);
            viewNum = view.findViewById(R.id.tv_view_num);
            postTime = view.findViewById(R.id.tv_post_time);

            view.setOnClickListener(v -> {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            });
            //长按事件
            view.setOnLongClickListener(v -> {
                mOnItemClickListener.onItemLongClick(view, getLayoutPosition());
                return true;
            });
        }
    }

    private NewsHotAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(NewsHotAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 点击响应事件
     */
    public interface OnItemClickListener {

        /**
         * 当RecyclerView某个被点击的时候回调
         *
         * @param view     点击item的视图
         * @param position 点击得到的数据
         */
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

    }
}

