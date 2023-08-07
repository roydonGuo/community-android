package com.roydon.community.aaatest;

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
 * @description 具有尾部的adapter
 */
public class MallGoodAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_FOOTER = 1;

    private final Context mContext;
    private List<MallGoodsVO> data;

    public void setData(List<MallGoodsVO> data) {
        this.data = data;
    }

    public MallGoodAdapter2(Context context) {
        this.mContext = context;
    }

    public MallGoodAdapter2(Context context, List<MallGoodsVO> data) {
        this.mContext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 根据视图类型创建 ViewHolder
        if (viewType == VIEW_TYPE_ITEM) {
            // 创建数据项视图的 ViewHolder
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_mall_good, parent, false);
            return new MallGoodHolder(itemView);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            // 创建尾部视图的 ViewHolder
            View footerView = LayoutInflater.from(mContext).inflate(R.layout.layout_not_more, parent, false);
            return new FooterHolder(footerView);
        }
        return null;
//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mall_good, parent, false);
//        return new MallGoodHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MallGoodHolder) {
            // 绑定数据项视图的数据
            MallGoodHolder itemViewHolder = (MallGoodHolder) holder;
            MallGoodsVO mallGoods = data.get(position);
            itemViewHolder.goodsTitle.setText(mallGoods.getGoodsTitle());
            itemViewHolder.goodsPrice.setText(mallGoods.getGoodsPrice() + "");
            itemViewHolder.stock.setText(mallGoods.getStock() + "");
            itemViewHolder.username.setText(mallGoods.getNickName());
            itemViewHolder.viewNum.setText(mallGoods.getViewNum() + "");
            Picasso.with(mContext).load(mallGoods.getGoodsImg()).into(itemViewHolder.goodsImg);
            Picasso.with(mContext).load(mallGoods.getAvatar()).transform(new CircleTransform()).into(itemViewHolder.avatar);
        } else if (holder instanceof FooterHolder) {
            // 绑定尾部视图的数据
            FooterHolder footerViewHolder = (FooterHolder) holder;
            // 设置尾部视图的内容
            // footerViewHolder.footerTextView.setText("Footer");
        }

    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        // 根据位置返回视图类型
        if (position == data.size()) {
            return VIEW_TYPE_FOOTER; // 尾部视图类型
        } else {
            return VIEW_TYPE_ITEM; // 数据项视图类型
        }
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
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(@NonNull View view) {
            super(view);
            view.setOnClickListener(v -> {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
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
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

    }
}
