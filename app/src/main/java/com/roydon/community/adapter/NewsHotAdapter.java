package com.roydon.community.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roydon.community.R;
import com.roydon.community.domain.vo.HotNews;
import com.roydon.community.utils.img.MyBitmapUtils;
import com.roydon.community.utils.string.DateUtils;
import com.roydon.community.utils.string.TimeAgoUtils;

import java.util.List;

public class NewsHotAdapter extends RecyclerView.Adapter<NewsHotAdapter.NewsHotHolder> {

    private final Context mContext;
    private List<HotNews> data;
    private MyBitmapUtils myBitmapUtils;

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
        // 新闻封面-myBitmapUtils缓存封面（缺点：无法适配gif，要想适配gif可以用Glide.with()）
        myBitmapUtils = new MyBitmapUtils(mContext);
        Bitmap imageFile = myBitmapUtils.disPlay(holder.coverImg, hotNews.getCoverImg());
        holder.coverImg.setImageBitmap(imageFile);
        holder.newsTitle.setText(hotNews.getNewsTitle());
        holder.source.setText(hotNews.getSource());
        holder.viewNum.setText(hotNews.getViewNum() + "");
        holder.postTime.setText(TimeAgoUtils.smartTime(DateUtils.date2LocalDateTime(hotNews.getPostTime())));
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
                mOnItemClickListener.onItemLongClick(v, getLayoutPosition());
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

