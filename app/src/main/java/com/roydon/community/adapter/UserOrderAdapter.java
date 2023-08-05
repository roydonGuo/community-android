package com.roydon.community.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roydon.community.R;
import com.roydon.community.domain.vo.MallOrderGoodsVO;
import com.roydon.community.domain.vo.MallOrderVO;

import java.util.List;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.UserOrderHolder> {

    private Context mContext;
    private List<MallOrderVO> datas;

    public void setDatas(List<MallOrderVO> datas) {
        this.datas = datas;
    }

    public UserOrderAdapter(Context context) {
        this.mContext = context;
    }

    public UserOrderAdapter(Context context, List<MallOrderVO> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public UserOrderAdapter.UserOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user_order, parent, false);
        return new UserOrderAdapter.UserOrderHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserOrderAdapter.UserOrderHolder holder, int position) {
        MallOrderVO orderVO = datas.get(position);
        // 获取订单状态
        String payStatus = orderVO.getPayStatus();
        String orderStatus = "未支付";
        if (payStatus.equals("1")) {
            orderStatus = "已支付";
        }
        holder.orderPayStatus.setText(orderStatus);
        holder.totalPrice.setText(orderVO.getTotalPrice() + "");

        List<MallOrderGoodsVO> mallOrderGoodsVOList = datas.get(position).getMallOrderGoodsVOList();
        holder.showOrderGoods(mallOrderGoodsVOList);
    }

    @Override
    public int getItemCount() {
        return (datas == null) ? 0 : datas.size();
    }

    public class UserOrderHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvOrderGoods;
        private UserOrderGoodsAdapter userOrderGoodsAdapter;
        private LinearLayoutManager linearLayoutManager;

        private TextView orderPayStatus, totalPrice;

        public void showOrderGoods(List<MallOrderGoodsVO> orderGoodsVOList) {
            userOrderGoodsAdapter.setDatas(orderGoodsVOList);
        }

        public UserOrderHolder(@NonNull View view) {
            super(view);
            orderPayStatus = view.findViewById(R.id.tv_order_pay_status);
            totalPrice = view.findViewById(R.id.tv_total_price);

            // 内层rv展示订单商品
            rvOrderGoods = view.findViewById(R.id.rv_user_order_goods);

            // 设置适配器
            linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvOrderGoods.setLayoutManager(linearLayoutManager);
            userOrderGoodsAdapter = new UserOrderGoodsAdapter(mContext);
            rvOrderGoods.setAdapter(userOrderGoodsAdapter);

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

    private UserOrderAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(UserOrderAdapter.OnItemClickListener onItemClickListener) {
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

