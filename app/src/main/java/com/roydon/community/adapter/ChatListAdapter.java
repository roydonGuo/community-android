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
import com.roydon.community.domain.entity.ChatList;
import com.roydon.community.listener.OnItemClickListener;
import com.roydon.community.utils.string.TimeAgoUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListHolder> {

    private Context context;
    private List<ChatList> list;
    private OnItemClickListener onItemClickListener;

    public ChatListAdapter() {
    }

    public ChatListAdapter(Context context) {
        this.context = context;
    }

    public ChatListAdapter(Context context, List<ChatList> list) {
        this.context = context;
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ChatList> getList() {
        return list;
    }

    public void setList(List<ChatList> list) {
        this.list = list;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ChatListAdapter.ChatListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new ChatListAdapter.ChatListHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ChatListHolder holder, int position) {
        ChatList chatList = list.get(position);
        Picasso.with(context).load(chatList.getReceiverImage()).into(holder.avatar);
        holder.username.setText(chatList.getReceiver());
        holder.newContent.setText(chatList.getNewContent());
        holder.time.setText(TimeAgoUtils.smartTime(chatList.getUpdateTime()));
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    public class ChatListHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView username;
        private TextView newContent;
        private TextView time;

        public ChatListHolder(@NonNull View view) {
            super(view);
            avatar = view.findViewById(R.id.iv_user_avatar);
            username = view.findViewById(R.id.tv_username);
            newContent = view.findViewById(R.id.tv_content);
            time = view.findViewById(R.id.tv_time);
            view.setOnClickListener(v -> {
                onItemClickListener.onItemClick(v, getLayoutPosition());
            });
            //长按事件
            view.setOnLongClickListener(v -> {
                onItemClickListener.onItemLongClick(v, getLayoutPosition());
                return true;
            });
        }
    }


}
