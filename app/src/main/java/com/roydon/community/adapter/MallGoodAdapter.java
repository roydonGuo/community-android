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

    private Context mContext;
    private List<MallGoodsVO> datas;

    public void setDatas(List<MallGoodsVO> datas) {
        this.datas = datas;
    }

    public MallGoodAdapter(Context context) {
        this.mContext = context;
    }

    public MallGoodAdapter(Context context, List<MallGoodsVO> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MallGoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mall_good, parent, false);
        return new MallGoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MallGoodHolder holder, int position) {
        MallGoodsVO mallGoods = datas.get(position);
        holder.goodsTitle.setText(mallGoods.getGoodsTitle());
        holder.goodsPrice.setText("￥" + mallGoods.getGoodsPrice());
        holder.stock.setText("库存" + mallGoods.getStock());
        holder.username.setText(mallGoods.getNickName());
        holder.viewNum.setText("浏览" + mallGoods.getViewNum());
        Picasso.with(mContext).load(mallGoods.getGoodsImg()).into(holder.goodsImg);
        Picasso.with(mContext).load(mallGoods.getAvatar()).transform(new CircleTransform()).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return (datas == null) ? 0 : datas.size();
    }

    public class MallGoodHolder extends RecyclerView.ViewHolder {
        private TextView goodsTitle;
        private TextView goodsPrice;
        private TextView stock;
        private TextView username;
        private TextView viewNum;
        private ImageView goodsImg;
        private ImageView avatar;

        public MallGoodHolder(@NonNull View view) {
            super(view);
            goodsTitle = view.findViewById(R.id.tv_goods_title);
            goodsPrice = view.findViewById(R.id.tv_goods_price);
            stock = view.findViewById(R.id.tv_stock);
            username = view.findViewById(R.id.tv_username);
            viewNum = view.findViewById(R.id.tv_viewNum);
            goodsImg = view.findViewById(R.id.iv_goodsImg);
            avatar = view.findViewById(R.id.tv_avatar);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, getLayoutPosition());
                }
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
