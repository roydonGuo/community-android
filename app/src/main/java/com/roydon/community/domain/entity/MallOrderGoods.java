package com.roydon.community.domain.entity;

import java.io.Serializable;

public class MallOrderGoods implements Serializable {
    private static final long serialVersionUID = 789602558626075892L;

    private String id;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 此商品总价格
     */
    private Double price;
    /**
     * 数量
     */
    private Integer count;
    private Double totalPrice;
    /**
     * 0未收货1已收货
     */
    private String receive;

}