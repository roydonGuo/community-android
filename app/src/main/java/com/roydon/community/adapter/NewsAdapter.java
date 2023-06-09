package com.roydon.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roydon.community.R;
import com.roydon.community.entity.AppNews;
import com.roydon.community.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AppNews> datas;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setDatas(List<AppNews> datas) {
        this.datas = datas;
    }

    public NewsAdapter(Context context) {
        this.mContext = context;
    }

    public NewsAdapter(Context context, List<AppNews> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        int type = Integer.parseInt(datas.get(position).getNewsType());
        return type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_one, parent, false);
            return new ViewHolderOne(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_three, parent, false);
            return new ViewHolderTwo(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        AppNews newsEntity = datas.get(position);
        if (type == 1) {
            ViewHolderOne vh = (ViewHolderOne) holder;
            vh.title.setText(newsEntity.getNewsTitle());
            vh.author.setText(newsEntity.getSource());
            vh.comment.setText(newsEntity.getViewNum() + "浏览 .");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vh.time.setText(sdf.format(newsEntity.getPostTime()));
            vh.appNews = newsEntity;
            Picasso.with(mContext)
                    .load(newsEntity.getCoverImg())
                    .transform(new CircleTransform())
                    .into(vh.header);

            Picasso.with(mContext)
                    .load(newsEntity.getCoverImg())
                    .into(vh.thumb);
        } else {
            ViewHolderTwo vh = (ViewHolderTwo) holder;
            vh.title.setText(newsEntity.getNewsTitle());
            vh.author.setText(newsEntity.getSource());
            vh.comment.setText(newsEntity.getViewNum() + "浏览 .");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vh.time.setText(sdf.format(newsEntity.getPostTime()));
            vh.appNews = newsEntity;
            Picasso.with(mContext)
                    .load(newsEntity.getCoverImg())
                    .transform(new CircleTransform())
                    .into(vh.header);
            Picasso.with(mContext)
                    .load(newsEntity.getCoverImg())
                    .into(vh.thumb);
        }

    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        } else {
            return 0;
        }
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;
        private TextView comment;
        private TextView time;
        private ImageView header;
        private ImageView thumb;
        private AppNews appNews;

        public ViewHolderOne(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            comment = view.findViewById(R.id.comment);
            time = view.findViewById(R.id.time);
            header = view.findViewById(R.id.header);
            thumb = view.findViewById(R.id.thumb);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(appNews);
                }
            });
        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView author;
        private TextView comment;
        private TextView time;
        private ImageView header;
        private ImageView thumb;
        private AppNews appNews;

        public ViewHolderTwo(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            comment = view.findViewById(R.id.comment);
            time = view.findViewById(R.id.time);
            header = view.findViewById(R.id.header);
            thumb = view.findViewById(R.id.thumb);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(appNews);
                }
            });
        }
    }

//    public class ViewHolderThree extends RecyclerView.ViewHolder {
//        private TextView title;
//        private TextView author;
//        private TextView comment;
//        private TextView time;
//        private ImageView header;
//        private ImageView pic1, pic2, pic3;
//        private AppNews newsEntity;
//
//        public ViewHolderThree(@NonNull View view) {
//            super(view);
//            title = view.findViewById(R.id.title);
//            author = view.findViewById(R.id.author);
//            comment = view.findViewById(R.id.comment);
//            time = view.findViewById(R.id.time);
//            header = view.findViewById(R.id.header);
//            pic1 = view.findViewById(R.id.pic1);
//            pic2 = view.findViewById(R.id.pic2);
//            pic3 = view.findViewById(R.id.pic3);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItemClickListener.onItemClick(newsEntity);
//                }
//            });
//        }
//    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }
}
