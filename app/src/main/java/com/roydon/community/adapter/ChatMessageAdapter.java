package com.roydon.community.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.roydon.community.R;
import com.roydon.community.chat.domain.ChatMessageDTO;
import com.roydon.community.listener.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ChatMessageDTO> list;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ChatMessageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ChatMessageAdapter(List<ChatMessageDTO> list) {
        this.list = list;
    }

    public ChatMessageAdapter(Context mContext, List<ChatMessageDTO> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public List<ChatMessageDTO> getList() {
        return list;
    }

    public void setList(List<ChatMessageDTO> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        boolean showType = list.get(position).isSendType();
        return showType ? 0 : 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            // 发送者
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_chat_left, parent, false);
            return new ChatMessageAdapter.ViewHolderLeft(view);
        } else {
            // 接收者
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_chat_right, parent, false);
            return new ChatMessageAdapter.ViewHolderRight(view);
        }
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        ChatMessageDTO chatMessageDTO = list.get(position);
        if (type == 1) {
            ChatMessageAdapter.ViewHolderLeft vh = (ChatMessageAdapter.ViewHolderLeft) holder;
            vh.content.setText(chatMessageDTO.getContent());
            Picasso.with(mContext).load(chatMessageDTO.getSenderAvatar()).into(vh.avatar);
        } else {
            ChatMessageAdapter.ViewHolderRight vh = (ChatMessageAdapter.ViewHolderRight) holder;
//            vh.time.setText(TimeAgoUtils.smartTimeFromDate(dateTime, chatMessage.getTime()));
            vh.content.setText(chatMessageDTO.getContent());
            Picasso.with(mContext).load(chatMessageDTO.getReceiverAvatar()).into(vh.avatar);
        }

    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    public class ViewHolderLeft extends RecyclerView.ViewHolder {
        private final TextView content, time;
        private final RoundedImageView avatar;

        public ViewHolderLeft(@NonNull View view) {
            super(view);
            content = view.findViewById(R.id.tv_content);
            time = view.findViewById(R.id.tv_time);
            avatar = view.findViewById(R.id.iv_user_avatar);
            view.setOnClickListener(v -> {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            });
            view.setOnLongClickListener(v -> {
                mOnItemClickListener.onItemLongClick(v, getLayoutPosition());
                return true;
            });
        }
    }

    public class ViewHolderRight extends RecyclerView.ViewHolder {
        private final TextView content, time;
        private final RoundedImageView avatar;

        public ViewHolderRight(@NonNull View view) {
            super(view);
            content = view.findViewById(R.id.tv_content);
            time = view.findViewById(R.id.tv_time);
            avatar = view.findViewById(R.id.iv_user_avatar);
            view.setOnClickListener(v -> {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            });
            view.setOnLongClickListener(v -> {
                mOnItemClickListener.onItemLongClick(v, getLayoutPosition());
                return true;
            });
        }
    }

}
