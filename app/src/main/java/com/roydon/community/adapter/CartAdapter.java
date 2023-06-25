package com.roydon.community.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.roydon.community.R;
import com.roydon.community.domain.entity.MallUserCartVO;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author roydon
 * @date 2023/6/25 3:31
 * @description community-android
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private Context mContext;
    private List<MallUserCartVO> datas;

    public void setDatas(List<MallUserCartVO> datas) {
        this.datas = datas;
    }

    public CartAdapter(Context context) {
        this.mContext = context;
    }

    public CartAdapter(Context context, List<MallUserCartVO> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public CartAdapter.CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mall_cart, parent, false);
        return new CartAdapter.CartHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartHolder holder, int position) {
        MallUserCartVO cartVO = datas.get(position);
        holder.goodsTitle.setText(cartVO.getGoodsTitle());
        holder.goodsPrice.setText("￥" + cartVO.getGoodsPrice());
        holder.goodsCount.setText(cartVO.getGoodsCount().toString());
        Picasso.with(mContext).load(cartVO.getGoodsImg()).into(holder.goodsImg);
    }

    @Override
    public int getItemCount() {
        return (datas == null) ? 0 : datas.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        private ImageView goodsImg;
        private TextView goodsTitle;
        private TextView goodsPrice;
        private Button goodsCount;

        public CartHolder(@NonNull View view) {
            super(view);
            goodsImg = view.findViewById(R.id.iv_goods_img);
            goodsTitle = view.findViewById(R.id.tv_goods_title);
            goodsPrice = view.findViewById(R.id.tv_goods_price);
            goodsCount = view.findViewById(R.id.btn_goods_count);
            view.setOnClickListener(v -> {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());

            });
            //长按事件
            view.setOnLongClickListener(v -> {
                mOnItemClickListener.onItemLongClick(view, getLayoutPosition());
                return true;
            });
        }
    }

    private CartAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(CartAdapter.OnItemClickListener onItemClickListener) {
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
