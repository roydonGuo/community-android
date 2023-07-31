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
import com.roydon.community.domain.vo.MallOrderGoodsVO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserOrderGoodsAdapter extends RecyclerView.Adapter<UserOrderGoodsAdapter.UserOrderGoodHolder> {

    private Context mContext;
    private List<MallOrderGoodsVO> datas;

    public void setDatas(List<MallOrderGoodsVO> datas) {
        this.datas = datas;
    }

    public UserOrderGoodsAdapter(Context context) {
        this.mContext = context;
    }

    public UserOrderGoodsAdapter(Context context, List<MallOrderGoodsVO> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public UserOrderGoodsAdapter.UserOrderGoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user_order_good, parent, false);
        return new UserOrderGoodsAdapter.UserOrderGoodHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserOrderGoodsAdapter.UserOrderGoodHolder holder, int position) {
        MallOrderGoodsVO goodsVO = datas.get(position);
        holder.goodsTitle.setText(goodsVO.getMallGoods().getGoodsTitle());
        holder.goodsPrice.setText(goodsVO.getPrice() + "");
        holder.goodsCount.setText(goodsVO.getCount() + "");
        Picasso.with(mContext).load(goodsVO.getMallGoods().getGoodsImg()).into(holder.goodsImg);
    }

    @Override
    public int getItemCount() {
        return (datas == null) ? 0 : datas.size();
    }

    public class UserOrderGoodHolder extends RecyclerView.ViewHolder {
        private ImageView goodsImg;
        private TextView goodsTitle;
        private TextView goodsPrice;
        private TextView goodsCount;

        public UserOrderGoodHolder(@NonNull View view) {
            super(view);
            goodsImg = view.findViewById(R.id.iv_goods_img);
            goodsTitle = view.findViewById(R.id.tv_goods_title);
            goodsPrice = view.findViewById(R.id.tv_goods_price);
            goodsCount = view.findViewById(R.id.tv_goods_count);
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

    private UserOrderGoodsAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(UserOrderGoodsAdapter.OnItemClickListener onItemClickListener) {
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
