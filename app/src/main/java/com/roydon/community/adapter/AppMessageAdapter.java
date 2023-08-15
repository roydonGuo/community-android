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
import com.roydon.community.domain.entity.AppMessage;

import java.util.List;

public class AppMessageAdapter extends RecyclerView.Adapter<AppMessageAdapter.AppMessageHolder> {

    private Context mContext;
    private List<AppMessage> datas;

    public void setDatas(List<AppMessage> datas) {
        this.datas = datas;
    }

    public AppMessageAdapter(Context context) {
        this.mContext = context;
    }

    public AppMessageAdapter(Context context, List<AppMessage> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public AppMessageAdapter.AppMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_app_message, parent, false);
        return new AppMessageAdapter.AppMessageHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AppMessageAdapter.AppMessageHolder holder, int position) {
        AppMessage appMessage = datas.get(position);
        holder.tvContent.setText(appMessage.getMessageContent());
    }

    @Override
    public int getItemCount() {
        return (datas == null) ? 0 : datas.size();
    }

    public class AppMessageHolder extends RecyclerView.ViewHolder {
        private TextView tvFrom, tvContent;

        public AppMessageHolder(@NonNull View view) {
            super(view);
            tvFrom = view.findViewById(R.id.tv_message_from);
            tvContent = view.findViewById(R.id.tv_message_content);
            view.setOnClickListener(v -> {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            });
            view.setOnLongClickListener(v -> {
                mOnItemClickListener.onItemLongClick(v, getLayoutPosition());
                return true;
            });
        }
    }

    private AppMessageAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(AppMessageAdapter.OnItemClickListener onItemClickListener) {
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
