package com.roydon.community.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roydon.community.R;
import com.roydon.community.domain.entity.AppHotline;
import com.roydon.community.listener.OnItemClickListener;

import java.util.List;

public class HotlineAdapter extends RecyclerView.Adapter<HotlineAdapter.HotlineHolder> {

    private final Context mContext;
    private List<AppHotline> data;

    public void setData(List<AppHotline> data) {
        this.data = data;
    }

    public HotlineAdapter(Context context) {
        this.mContext = context;
    }

    public HotlineAdapter(Context context, List<AppHotline> data) {
        this.mContext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public HotlineAdapter.HotlineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hotline, parent, false);
        return new HotlineAdapter.HotlineHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HotlineAdapter.HotlineHolder holder, int position) {
        AppHotline hotline = data.get(position);
        holder.leader.setText(hotline.getLeader());
        holder.telephone.setText(hotline.getTelephone());
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public class HotlineHolder extends RecyclerView.ViewHolder {
        private final TextView leader, telephone;

        public HotlineHolder(@NonNull View view) {
            super(view);
            leader = view.findViewById(R.id.tv_hotline_leader);
            telephone = view.findViewById(R.id.tv_hotline_telephone);
            view.setOnClickListener(v -> {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            });
            view.setOnLongClickListener(v -> {
                mOnItemClickListener.onItemLongClick(v, getLayoutPosition());
                return true;
            });
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 点击响应事件
     */
//    public interface OnItemClickListener {
//
//        /**
//         * 当RecyclerView某个被点击的时候回调
//         *
//         * @param view     点击item的视图
//         * @param position 点击得到的数据
//         */
//        void onItemClick(View view, int position);
//
//        void onItemLongClick(View view, int position);
//
//    }
}

