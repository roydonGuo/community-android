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
import com.roydon.community.domain.entity.MallGoodsVO;
import com.roydon.community.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author roydon
 * @date 2023/6/21 22:39
 * @description community-android
 */
public class MallGoodAdapter extends RecyclerView.Adapter<MallGoodAdapter.MallGoodHolder> {

    private final Context mContext;
    private List<MallGoodsVO> data;

    public void setData(List<MallGoodsVO> data) {
        this.data = data;
    }

    public MallGoodAdapter(Context context) {
        this.mContext = context;
    }

    public MallGoodAdapter(Context context, List<MallGoodsVO> data) {
        this.mContext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MallGoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mall_good, parent, false);
        return new MallGoodHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MallGoodHolder holder, int position) {
        MallGoodsVO mallGoods = data.get(position);
        holder.goodsTitle.setText(mallGoods.getGoodsTitle());
        holder.goodsPrice.setText(mallGoods.getGoodsPrice() + "");
        holder.stock.setText(mallGoods.getStock() + "");
        holder.username.setText(mallGoods.getNickName());
        holder.viewNum.setText(mallGoods.getViewNum() + "");
        Picasso.with(mContext).load(mallGoods.getGoodsImg()).into(holder.goodsImg);
        Picasso.with(mContext).load(mallGoods.getAvatar()).transform(new CircleTransform()).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public class MallGoodHolder extends RecyclerView.ViewHolder {
        private final TextView goodsTitle, goodsPrice, stock, username, viewNum;
        private final ImageView goodsImg, avatar;

        public MallGoodHolder(@NonNull View view) {
            super(view);
            goodsTitle = view.findViewById(R.id.tv_goods_title);
            goodsPrice = view.findViewById(R.id.tv_goods_price);
            stock = view.findViewById(R.id.tv_stock);
            username = view.findViewById(R.id.tv_username);
            viewNum = view.findViewById(R.id.tv_view_num);
            goodsImg = view.findViewById(R.id.iv_goodsImg);
            avatar = view.findViewById(R.id.tv_avatar);
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
