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

    public MallOrderGoods() {
    }

    public MallOrderGoods(String id, String orderId, String goodsId, Double price, Integer count, Double totalPrice, String receive) {
        this.id = id;
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.price = price;
        this.count = count;
        this.totalPrice = totalPrice;
        this.receive = receive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }
}