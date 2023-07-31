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
import com.roydon.community.domain.entity.MallUserAddress;

import java.util.List;

/**
 * @author roydon
 * @date 2023/6/25 12:55
 * @description AddressAdapter
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {

    private Context mContext;
    private List<MallUserAddress> datas;

    public void setDatas(List<MallUserAddress> datas) {
        this.datas = datas;
    }

    public AddressAdapter(Context context) {
        this.mContext = context;
    }

    public AddressAdapter(Context context, List<MallUserAddress> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public AddressAdapter.AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mall_address, parent, false);
        return new AddressAdapter.AddressHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.AddressHolder holder, int position) {
        MallUserAddress address = datas.get(position);
        holder.addressUser.setText(address.getNickname() + " " + address.getTelephone());
        holder.addressComplete.setText(address.getCompleteAddress());
        holder.isDefault.setText(address.getIsDefault().equals("1") ? "默认" : "");
    }

    @Override
    public int getItemCount() {
        return (datas == null) ? 0 : datas.size();
    }

    public class AddressHolder extends RecyclerView.ViewHolder {
        private TextView addressUser, addressComplete, isDefault;

        public AddressHolder(@NonNull View view) {
            super(view);
            addressUser = view.findViewById(R.id.tv_address_user);
            addressComplete = view.findViewById(R.id.tv_address_complete);
            isDefault = view.findViewById(R.id.address_is_default);
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

    private AddressAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(AddressAdapter.OnItemClickListener onItemClickListener) {
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
        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);

    }
}
